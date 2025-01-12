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
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fusesource.jansi.Ansi;

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

    private final String[] projectTemplates = {"openapi.yaml", "pages.xml"};
    private final String[] reactComponentTemplates = {"InputField", "KeyValuePair", "RecursiveKeyValuePair", "Alert", "HeroSection", "SearchBar", "Button", "CardSection"};
    private final String[] reactCommonTemplates = {"Utils"};
    private final String[] cssComponentTemplates = {"InputField", "KeyValuePair", "Alert", "HeroSection", "SearchBar", "Button", "CardSection", "NavBar", "Form"};
    private final String[] cssCommonTemplates = {"App", "index"};
    private final String[] projectSubDirs = {"styles/components", "styles/pages", "styles/custom_styles"};
    private final String[] buildSubDirs = {"build/src/components", "build/src/pages", "build/src/custom_styles", "build/src/common"};
    private final String[] npmPackages = {"react-router-dom"};

    public ProjectManager() {
        this.fileHandler = new FileHandler();
        this.commandHandler = new CommandHandler();
        this.configHandler = new ConfigHandler("config.properties");
    }

    private boolean updateProjectName() {
        if (!this.configHandler.isPropertiesFileAvailable()) {
            return false;
        }
        setProjectName(this.configHandler.readProperty("projectName"));
        return true;
    }

    private void copyTemplateFiles() {
        String buildSrcDir = projectName + "\\build\\src\\";
        String stylesDir = projectName + "\\styles\\";

        for (String reactTemplate : reactComponentTemplates) {
            String resourcePath = "/templates/react/components/" + reactTemplate + ".jsx";
            fileHandler.copyResource(resourcePath, new File(buildSrcDir + "components\\" + reactTemplate + ".jsx"));
        }

        for (String reactTemplate : reactCommonTemplates) {
            String resourcePath = "/templates/react/common/" + reactTemplate + ".js";
            fileHandler.copyResource(resourcePath, new File(buildSrcDir + "common\\" + reactTemplate + ".js"));
        }

        for (String cssTemplate : cssComponentTemplates) {
            String resourcePath = "/templates/css/components/" + cssTemplate + ".css";
            fileHandler.copyResource(resourcePath, new File(stylesDir + "components\\" + cssTemplate + ".css"));
        }

        for (String cssTemplate : cssCommonTemplates) {
            String resourcePath = "/templates/css/common/" + cssTemplate + ".css";
            fileHandler.copyResource(resourcePath, new File(stylesDir + cssTemplate + ".css"));
        }

        for (String projectTemplate : projectTemplates) {
            String resourcePath = "/templates/project/" + projectTemplate;
            fileHandler.copyResource(resourcePath, new File(projectName + "\\" + projectTemplate));
        }

    }

    public boolean generateCode() {
        if(!updateProjectName()) return false;

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
            addUserStyles();
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage());
        }
        return true;
    }

    public boolean generateClientAPI() {
        if(!updateProjectName()) return false;
        logger.info("Generating ClientAPI...");

        ClientAPIGenerator clientAPIGenerator = new ClientAPIGenerator();
        File specDir = new File(projectName + "\\openapi.yaml");
        File outputDir = new File(projectName + "\\build\\src\\client_api");
        clientAPIGenerator.generateClientAPI(specDir, outputDir, "typescript");
        logger.info("✔ Client API Generated Successfully!");

        return true;
    }

    public boolean addUserStyles() {
        if(!updateProjectName()) return false;

        String buildSrcDir = projectName + "\\build\\src\\";
        String stylesDir = projectName + "\\styles\\";
        logger.info("Applying User Styles... ");

        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "components"), new File(buildSrcDir + "components"));
        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "pages"), new File(buildSrcDir + "pages"));
        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "custom_styles"), new File(buildSrcDir + "custom_styles"));
        fileHandler.copyFile(new File(stylesDir + "index.css"), new File(buildSrcDir + "index.css"));
        fileHandler.copyFile(new File(stylesDir + "App.css"), new File(buildSrcDir + "App.css"));

        logger.info("✔ Build Complete!");
        return true;
    }

    public void initializeProject(String projectName) {
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

        copyTemplateFiles();

        logger.info("Project initialized successfully.");
    }


//    public void initializeProject() {
//        logger.info("Project Initialization Started...");
//        File projectDir = new File(projectName);
//        if (!projectDir.exists()) {
//            if (projectDir.mkdirs()) {
//                logger.info("Project directory {} created.", projectName);
//            } else {
//                logger.error("Failed to create project directory {}." ,projectName);
//                return;
//            }
//        } else {
//            logger.warn("Project directory {} already exists.", projectName);
//        }
//
//        try {
//            this.fileHandler.createFile(projectDir, "openapi.yaml", "# OpenAPI specification\nopenapi: \"3.0.0\"\ninfo:\n  title: \"Sample API\"\n  version: \"1.0.0\"\npaths: {}");
//            this.fileHandler.createFile(projectDir, "pages.xml", "<pages>\n    <!-- Page configurations go here -->\n</pages>");
//            this.fileHandler.createDirectory(projectDir, "styles/components");
//            this.fileHandler.createDirectory(projectDir, "styles/pages");
//            this.fileHandler.createDirectory(projectDir, "styles/custom_styles");
//            this.fileHandler.createDirectory(projectDir, "js");
//            this.commandHandler.createReactApp(ProjectManager.projectName);
//            this.commandHandler.installNpmPackage(ProjectManager.projectName, "react-router-dom");
//            this.fileHandler.createDirectory(projectDir, "build/src/components");
//            this.fileHandler.createDirectory(projectDir, "build/src/pages");
//            this.fileHandler.createDirectory(projectDir, "build/src/custom_styles");
//            this.fileHandler.createDirectory(projectDir, "build/src/common");
//            copyTemplateFiles();
//            logger.info("Project initialized successfully.");
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//    }

    public boolean runProject() {
        if(!updateProjectName()) return false;

        this.commandHandler.runReactApp(getProjectName());

        return true;
    }

    public boolean buildProject() {
        if(!updateProjectName()) return false;

    public void generateAll() {
        logger.info("Generating ClientAPI and Frontend...");
        generateCode();
        generateClientAPI();

        return true;
    }

    public boolean test() {
        logger.info("Starting Testing...");
        if(!updateProjectName()) return false;

        return true;
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
