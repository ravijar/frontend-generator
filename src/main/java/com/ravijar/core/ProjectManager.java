package com.ravijar.core;

import com.ravijar.config.FreeMarkerConfig;
import com.ravijar.generator.ClientAPIGenerator;
import com.ravijar.generator.ReactGenerator;
import com.ravijar.handler.CommandHandler;
import com.ravijar.handler.FileHandler;
import com.ravijar.parser.XMLParser;
import com.ravijar.model.PageDTO;
import com.ravijar.model.xml.Page;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProjectManager {
    private static final Logger logger = LogManager.getLogger(ProjectManager.class);

    private static String projectName = "Untitled";
    private final FileHandler fileHandler;
    private final CommandHandler commandHandler;

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
    }

    public static void setProjectName(String projectName) {
        ProjectManager.projectName = projectName;
    }

    public static String getProjectName() {
        return ProjectManager.projectName;
    }

    private void copyTemplateFiles() {
        String buildSrcDir = ProjectManager.projectName + "\\build\\src\\";
        String stylesDir = ProjectManager.projectName + "\\styles\\";

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
            fileHandler.copyResource(resourcePath, new File(ProjectManager.projectName + "\\" + projectTemplate));
        }

    }

    public void generateCode() {
        FreeMarkerConfig freeMarkerConfig = new FreeMarkerConfig();

        XMLParser xmlParser = new XMLParser(ProjectManager.projectName);
        List<Page> pages = xmlParser.getPages();

        File pageOutputDir = new File(ProjectManager.projectName + "\\build\\src\\pages");
        this.fileHandler.createDirectoryIfNotExists(pageOutputDir);

        File appOutputDir = new File(ProjectManager.projectName + "\\build\\src");
        this.fileHandler.createDirectoryIfNotExists(appOutputDir);

        File componentOutputDir = new File(ProjectManager.projectName + "\\build\\src\\components");
        this.fileHandler.createDirectoryIfNotExists(componentOutputDir);

        File userStylesDir = new File(ProjectManager.projectName + "\\styles");
        this.fileHandler.createDirectoryIfNotExists(userStylesDir);

        try {
            Configuration cfg = freeMarkerConfig.getConfiguration();
            ReactGenerator reactGenerator = new ReactGenerator(cfg);

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
    }

    public void generateClientAPI() {
        ClientAPIGenerator clientAPIGenerator = new ClientAPIGenerator();
        File specDir = new File(projectName + "\\openapi.yaml");
        File outputDir = new File(projectName + "\\build\\src\\client_api");
        clientAPIGenerator.generateClientAPI(specDir, outputDir, "typescript");
    }

    public void addUserStyles() {
        String buildSrcDir = ProjectManager.projectName + "\\build\\src\\";
        String stylesDir = ProjectManager.projectName + "\\styles\\";

        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "components"), new File(buildSrcDir + "components"));
        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "pages"), new File(buildSrcDir + "pages"));
        fileHandler.copyAllFilesFromDirectory(new File(stylesDir + "custom_styles"), new File(buildSrcDir + "custom_styles"));
        fileHandler.copyFile(new File(stylesDir + "index.css"), new File(buildSrcDir + "index.css"));
        fileHandler.copyFile(new File(stylesDir + "App.css"), new File(buildSrcDir + "App.css"));
    }

    public void initializeProject() {
        File projectDir = new File(projectName);
        this.fileHandler.createDirectoryIfNotExists(projectDir);

        for (String subDir : projectSubDirs) {
            this.fileHandler.createSubDirectory(projectDir, subDir);
        }

        this.commandHandler.createReactApp(ProjectManager.projectName);

        for (String npmPackage : npmPackages) {
            this.commandHandler.installNpmPackage(ProjectManager.projectName, npmPackage);
        }

        for (String subDir : buildSubDirs) {
            this.fileHandler.createSubDirectory(projectDir, subDir);
        }

        copyTemplateFiles();

        logger.info("Project initialized successfully.");
    }

    public void runProject() {
        this.commandHandler.runReactApp(ProjectManager.projectName);
    }

    public void buildProject() {
        generateCode();
        generateClientAPI();
    }

    public void test() {

    }

    @Deprecated
    private void copyUserFiles() {
        String buildSrcDir = ProjectManager.projectName + "\\build\\src\\";
        String stylesDir = ProjectManager.projectName + "\\styles\\";

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

        File pageOutputDir = new File(ProjectManager.projectName + "\\build\\src\\pages");
        if (!pageOutputDir.exists()) {
            pageOutputDir.mkdirs();
        }

        File appOutputDir = new File(ProjectManager.projectName + "\\build\\src");
        if (!appOutputDir.exists()) {
            appOutputDir.mkdirs();
        }

        try {
            Configuration cfg = freeMarkerConfig.getConfiguration();
            ReactGenerator reactGenerator = new ReactGenerator(cfg);

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
