package com.ravijar.core;

import com.ravijar.config.FreeMarkerConfig;
import com.ravijar.generator.ClientAPIGenerator;
import com.ravijar.generator.ReactGenerator;
import com.ravijar.handler.CommandHandler;
import com.ravijar.handler.ConfigHandler;
import com.ravijar.handler.FileHandler;
import com.ravijar.parser.OpenAPIParser;
import com.ravijar.parser.XMLParser;
import com.ravijar.model.xml.Page;
import com.ravijar.validators.XMLValidator;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ProjectManager {
    private static final Logger logger = LogManager.getLogger(ProjectManager.class);

    @Getter
    @Setter
    private String projectName;

    private final FileHandler fileHandler;
    private final CommandHandler commandHandler;
    private final ConfigHandler configHandler;
    private final String[] projectSubDirs = {"styles/components", "styles/pages", "styles/custom_styles"};
    private final String[] buildSubDirs = {"build/src/components", "build/src/pages", "build/src/custom_styles", "build/src/common", "build/src/auth"};
    private final String[] npmPackages = {"react-router-dom", "@fortawesome/react-fontawesome", "@fortawesome/free-regular-svg-icons", "@fortawesome/free-solid-svg-icons", "@react-oauth/google", "react-select"};
    private static final String SOURCE_ROOT_PATH = "templates";

    public ProjectManager() {
        this.fileHandler = new FileHandler();
        this.commandHandler = CommandHandler.getCommandHandler();
        this.configHandler = new ConfigHandler("config.properties");
    }

    private boolean setProjectNameFromConfig() {
        if (!this.configHandler.isPropertiesFileAvailable()) {
            return false;
        }
        setProjectName(this.configHandler.readProperty("projectName"));
        return true;
    }

    public boolean generateFrontend() {
        if (!setProjectNameFromConfig()) return false;

        logger.info("Generating Frontend...");

        FreeMarkerConfig freeMarkerConfig = new FreeMarkerConfig();
        OpenAPIParser openAPIParser = new OpenAPIParser(projectName + "\\openapi.yaml");

        XMLParser xmlParser = new XMLParser(getProjectName());
        List<Page> pages = xmlParser.getPages();

        File pageOutputDir = new File(projectName + "\\build\\src\\pages");
        this.fileHandler.createDirectoryIfNotExists(pageOutputDir);

        File appOutputDir = new File(projectName + "\\build\\src");
        this.fileHandler.createDirectoryIfNotExists(appOutputDir);

        File componentOutputDir = new File(projectName + "\\build\\src\\components");
        this.fileHandler.createDirectoryIfNotExists(componentOutputDir);

        File userStylesDir = new File(projectName + "\\styles");
        this.fileHandler.createDirectoryIfNotExists(userStylesDir);

        this.fileHandler.copyFile(new File(projectName + "\\.env"), new File(projectName + "\\build\\.env"));

        try {
            Configuration cfg = freeMarkerConfig.getConfiguration();
            ReactGenerator reactGenerator = new ReactGenerator(cfg, openAPIParser);

            for (Page page : pages) {
                reactGenerator.generatePage(
                        pageOutputDir.getAbsolutePath(),
                        componentOutputDir.getAbsolutePath(),
                        userStylesDir.getAbsolutePath(),
                        page
                );
            }
            reactGenerator.generateAppPage(appOutputDir.getAbsolutePath());
            reactGenerator.generateNavBar(componentOutputDir.getAbsolutePath());
            applyUserStyles();
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage());
        }

        logger.info("Frontend Generated Successfully!");

        return true;
    }

    public boolean generateClientAPI() {
        if (!setProjectNameFromConfig()) return false;

        logger.info("Generating ClientAPI...");

        ClientAPIGenerator clientAPIGenerator = new ClientAPIGenerator();
        File specDir = new File(projectName + "\\openapi.yaml");
        File outputDir = new File(projectName + "\\build\\src\\client_api");
        clientAPIGenerator.generateClientAPI(specDir, outputDir, "typescript");

        logger.info("Client API Generated Successfully!");

        return true;
    }

    public boolean applyUserStyles() {
        if (!setProjectNameFromConfig()) return false;

        logger.info("Applying User Styles... ");

        String buildSrcDir = projectName + "\\build\\src\\";
        String stylesDir = projectName + "\\styles\\";

        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "components"), new File(buildSrcDir + "components"));
        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "pages"), new File(buildSrcDir + "pages"));
        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "custom_styles"), new File(buildSrcDir + "custom_styles"));
        fileHandler.copyFile(new File(stylesDir + "index.css"), new File(buildSrcDir + "index.css"));
        fileHandler.copyFile(new File(stylesDir + "App.css"), new File(buildSrcDir + "App.css"));

        logger.info("User Styles Applied Successfully!");

        return true;
    }

    public void initializeProject(String projectName) {
        logger.info("Project Initialization Started...");

        // Check if project is already initialized
        if (configHandler.isProjectInitialized()) {
            logger.warn("Project is already initialized. Do you want to override? (yes/no)");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String response = reader.readLine().trim().toLowerCase();
                
                if (!response.equals("yes")) {
                    logger.info("Initialization cancelled by user.");
                    return;
                }
                
                // Clean up existing project
                cleanupExistingProject();
            } catch (IOException e) {
                logger.error("Error reading user input: {}", e.getMessage());
                return;
            }
        }

        this.configHandler.createPropertiesFile(projectName);
        setProjectName(projectName);

        File projectDir = new File(projectName);
        this.fileHandler.createDirectoryIfNotExists(projectDir);

        for (String subDir : projectSubDirs) {
            this.fileHandler.createSubDirectory(projectDir, subDir);
        }

        this.commandHandler.createReactApp(getProjectName());

        for (String npmPackage : npmPackages) {
            this.commandHandler.installNpmPackage(projectName, npmPackage);
        }

        for (String subDir : buildSubDirs) {
            this.fileHandler.createSubDirectory(projectDir, subDir);
        }

        fileHandler.copyAllTemplates(SOURCE_ROOT_PATH, projectName);

        logger.info("Project initialized successfully!");
    }

    private void cleanupExistingProject() {
        logger.info("Cleaning up existing project...");
        
        // Get the old project name before clearing configuration
        String oldProjectName = this.configHandler.readProperty("projectName");
        if (oldProjectName != null) {
            // Clean up the old project directory
            fileHandler.cleanupProjectDirectory(oldProjectName);
        }
        
        // Clear configuration after cleanup
        configHandler.clearConfiguration();
    }

    public boolean runProject() {
        if (!setProjectNameFromConfig()) return false;

        this.commandHandler.runReactApp(getProjectName());

        return true;
    }

    public boolean generateAll() {
        if (!setProjectNameFromConfig()) return false;
        boolean isValid = validateXML(projectName + "\\pages.xml");
        if (!isValid) return false;
        logger.info("Generating ClientAPI and Frontend...");
        generateFrontend();
        generateClientAPI();
        return true;
    }

    public boolean test() {
        if (!setProjectNameFromConfig()) return false;

        logger.info("Starting Testing...");

        return true;
    }

    public boolean validateXML(String xmlPath) {
        XMLValidator xmlValidator = new XMLValidator();
        try {
            xmlValidator.isValid(xmlPath);
            logger.info("XML Validated!");
            return true;
        } catch (SAXException e) {
            logger.error("Invalid XML File!");
            logger.error(e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
