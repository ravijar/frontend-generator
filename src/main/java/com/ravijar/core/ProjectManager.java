package com.ravijar.core;

import com.ravijar.config.FreeMarkerConfig;
import com.ravijar.generator.ClientAPIGenerator;
import com.ravijar.generator.ReactGenerator;
import com.ravijar.handler.CommandHandler;
import com.ravijar.handler.ConfigHandler;
import com.ravijar.handler.FileHandler;
import com.ravijar.parser.OpenAPIParser;
import com.ravijar.parser.XMLParser;
import com.ravijar.model.PageDTO;
import com.ravijar.model.xml.Page;
import com.ravijar.validators.XMLValidator;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProjectManager {
    private static final Logger logger = LogManager.getLogger(ProjectManager.class);

    @Getter
    @Setter
    private String projectName;

    private final FileHandler fileHandler;
    private final CommandHandler commandHandler;
    private final ConfigHandler configHandler;
    private final String[] cssComponentTemplates = {"InputField", "KeyValuePair", "Alert", "HeroSection", "SearchBar", "Button", "CardSection", "NavBar", "Form"};
    private final String[] projectSubDirs = {"styles/components", "styles/pages", "styles/custom_styles"};
    private final String[] buildSubDirs = {"build/src/components", "build/src/pages", "build/src/custom_styles", "build/src/common"};
    private final String[] npmPackages = {"react-router-dom", "@fortawesome/react-fontawesome", "@fortawesome/free-regular-svg-icons"};
    private static final String SOURCE_ROOT_PATH="templates";

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
        if(!setProjectNameFromConfig()) return false;

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
            reactGenerator.generateAppPage(appOutputDir.getAbsolutePath(), pages);
            reactGenerator.generateNavBar(componentOutputDir.getAbsolutePath(), pages);
            applyUserStyles();
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage());
        }

        logger.info("✔ Frontend Generated Successfully!");

        return true;
    }

    public boolean generateClientAPI() {
        if(!setProjectNameFromConfig()) return false;

        logger.info("Generating ClientAPI...");

        ClientAPIGenerator clientAPIGenerator = new ClientAPIGenerator();
        File specDir = new File(projectName + "\\openapi.yaml");
        File outputDir = new File(projectName + "\\build\\src\\client_api");
        clientAPIGenerator.generateClientAPI(specDir, outputDir, "typescript");

        logger.info("✔ Client API Generated Successfully!");

        return true;
    }

    public boolean applyUserStyles() {
        if(!setProjectNameFromConfig()) return false;

        logger.info("Applying User Styles... ");

        String buildSrcDir = projectName + "\\build\\src\\";
        String stylesDir = projectName + "\\styles\\";

        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "components"), new File(buildSrcDir + "components"));
        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "pages"), new File(buildSrcDir + "pages"));
        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "custom_styles"), new File(buildSrcDir + "custom_styles"));
        fileHandler.copyFile(new File(stylesDir + "index.css"), new File(buildSrcDir + "index.css"));
        fileHandler.copyFile(new File(stylesDir + "App.css"), new File(buildSrcDir + "App.css"));

        logger.info("✔ User Styles Applied Successfully!");

        return true;
    }

    public void initializeProject(String projectName) {
        logger.info("Project Initialization Started...");

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

        fileHandler.copyAllTemplates(SOURCE_ROOT_PATH,projectName);

        logger.info("✔ Project initialized successfully!");
    }

    public boolean runProject() {
        if(!setProjectNameFromConfig()) return false;

        this.commandHandler.runReactApp(getProjectName());

        return true;
    }

    public boolean generateAll() {
        if(!setProjectNameFromConfig()) return false;
        boolean isValid=validateXML(projectName+"\\pages.xml");
        if(!isValid) return false;
        logger.info("Generating ClientAPI and Frontend...");
        generateFrontend();
        generateClientAPI();
        return true;
    }

    public boolean test() {
        if(!setProjectNameFromConfig()) return false;

        logger.info("Starting Testing...");

        return true;
    }

    public boolean validateXML(String xmlPath){
        XMLValidator xmlValidator=new XMLValidator();
        try {
            xmlValidator.isValid(xmlPath);
            logger.info("XML Validated!");
            return true;
        } catch (SAXException e) {
            logger.error("Invalid XML File!");
            logger.error(e.getMessage());
            return false;
        } catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    @Deprecated
    private void copyUserFiles() {
        String buildSrcDir = projectName + "\\build\\src\\";
        String stylesDir = projectName + "\\styles\\";

        for (String cssTemplate : cssComponentTemplates) {
            File sourceFile = new File(stylesDir + "components\\" + cssTemplate + ".css");
            if (cssTemplate.equals("Page")) {
                fileHandler.copyFile(sourceFile, new File(buildSrcDir + "pages\\" + cssTemplate + ".css"));
            } else {
                fileHandler.copyFile(sourceFile, new File(buildSrcDir + "components\\" + cssTemplate + ".css"));
            }
        }

        fileHandler.copyFile(new File(stylesDir + "index.css"), new File(buildSrcDir + "index.css"));

        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "pages"), new File(buildSrcDir + "customStyles"));
    }

    @Deprecated
    private void generatePages(List<PageDTO> pageDTOs) {
        FreeMarkerConfig freeMarkerConfig = new FreeMarkerConfig();
        OpenAPIParser openAPIParser = new OpenAPIParser(projectName + "\\openapi.yaml");

        File pageOutputDir = new File(projectName + "\\build\\src\\pages");
        if (!pageOutputDir.exists()) {
            pageOutputDir.mkdirs();
        }

        File appOutputDir = new File(projectName + "\\build\\src");
        if (!appOutputDir.exists()) {
            appOutputDir.mkdirs();
        }

        try {
            Configuration cfg = freeMarkerConfig.getConfiguration();
            ReactGenerator reactGenerator = new ReactGenerator(cfg, openAPIParser);

            for (PageDTO pageDTO : pageDTOs) {
                reactGenerator.createPage(pageOutputDir.getAbsolutePath(), pageDTO);
            }
            reactGenerator.updateAppPage(appOutputDir.getAbsolutePath(), pageDTOs);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage());
        }
    }

    @Deprecated
    private void checkCustomStyleFiles(List<PageDTO> pageDTOs) {
        for (PageDTO pageDTO : pageDTOs) {
            File customStyleFile = new File(projectName + "\\styles\\pages\\" + pageDTO.getPageName() + "Styles.js");
            if (customStyleFile.exists()) {
                pageDTO.setCustomStyled(true);
            }
        }
    }
}
