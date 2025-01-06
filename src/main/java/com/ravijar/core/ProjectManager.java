package com.ravijar.core;

import com.ravijar.config.FreeMarkerConfig;
import com.ravijar.handler.CommandHandler;
import com.ravijar.handler.FileHandler;
import com.ravijar.handler.PagesFileHandler;
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
    private final String[] reactComponentTemplates = {"InputField", "KeyValuePair", "RecursiveKeyValuePair", "Alert", "HeroSection", "SearchBar", "Button", "CardSection"};
    private final String[] reactCommonTemplates = {"Utils"};
    private final String[] cssComponentTemplates = {"InputField", "KeyValuePair", "Page", "Alert", "HeroSection", "SearchBar", "Button", "CardSection", "NavBar", "Form"};

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
            fileHandler.copyResource("/templates/react/components/" + reactTemplate + ".jsx", new File(buildSrcDir + "components\\" + reactTemplate + ".jsx"));
        }

        for (String reactTemplate : reactCommonTemplates) {
            fileHandler.copyResource("/templates/react/common/" + reactTemplate + ".js", new File(buildSrcDir + "common\\" + reactTemplate + ".js"));
        }

        for (String cssTemplate : cssComponentTemplates) {
            String resourcePath = "/templates/css/components/" + cssTemplate + ".css";
            if (cssTemplate.equals("Page")) {
                fileHandler.copyResource(resourcePath, new File(buildSrcDir + "pages\\" + cssTemplate + ".css"));
            } else {
                fileHandler.copyResource(resourcePath, new File(buildSrcDir + "components\\" + cssTemplate + ".css"));
            }
            fileHandler.copyResource(resourcePath, new File(stylesDir + "components\\" + cssTemplate + ".css"));
        }

        fileHandler.copyFile(new File(buildSrcDir + "index.css"), new File(stylesDir + "index.css"));
    }

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
            ReactCodeGenerator codeGenerator = new ReactCodeGenerator(cfg);

            for (PageDTO pageDTO : pageDTOs) {
                codeGenerator.createPage(pageOutputDir.getAbsolutePath(), pageDTO);
            }
            codeGenerator.updateAppPage(appOutputDir.getAbsolutePath(), pageDTOs);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage());
        }
    }

    private void generatePagesNew(List<Page> pages) {
        FreeMarkerConfig freeMarkerConfig = new FreeMarkerConfig();

        File pageOutputDir = new File(ProjectManager.projectName + "\\build\\src\\pages");
        if (!pageOutputDir.exists()) {
            pageOutputDir.mkdirs();
        }

        File appOutputDir = new File(ProjectManager.projectName + "\\build\\src");
        if (!appOutputDir.exists()) {
            appOutputDir.mkdirs();
        }

        File componentOutputDir = new File(ProjectManager.projectName + "\\build\\src\\components");
        if (!componentOutputDir.exists()) {
            componentOutputDir.mkdirs();
        }

        try {
            Configuration cfg = freeMarkerConfig.getConfiguration();
            ReactCodeGenerator codeGenerator = new ReactCodeGenerator(cfg);

            for (Page page : pages) {
                codeGenerator.generatePage(pageOutputDir.getAbsolutePath(), componentOutputDir.getAbsolutePath(), page);
            }
            codeGenerator.generateAppPage(appOutputDir.getAbsolutePath(), pages);
            codeGenerator.generateNavBar(componentOutputDir.getAbsolutePath(), pages);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage());
        }
    }

    private void createClientApi() {
        ClientApiGenerator clientApiGenerator = new ClientApiGenerator();
        File specDir = new File(projectName + "\\openapi.yaml");
        File outputDir = new File(projectName + "\\build\\src\\client_api");
        clientApiGenerator.generateClientApi(specDir, outputDir, "typescript");
    }

    private void checkCustomStyleFiles(List<PageDTO> pageDTOs) {
        for (PageDTO pageDTO : pageDTOs) {
            File customStyleFile = new File(projectName + "\\styles\\pages\\" + pageDTO.getPageName() + "Styles.js");
            if (customStyleFile.exists()) {
                pageDTO.setCustomStyled(true);
            }
        }
    }

    public void initializeProject() {
        File projectDir = new File(projectName);
        if (!projectDir.exists()) {
            if (projectDir.mkdirs()) {
                logger.info("Project directory {} created.", projectName);
            } else {
                logger.error("Failed to create project directory {}." ,projectName);
                return;
            }
        } else {
            logger.warn("Project directory {} already exists.", projectName);
        }

        try {
            this.fileHandler.createFile(projectDir, "openapi.yaml", "# OpenAPI specification\nopenapi: \"3.0.0\"\ninfo:\n  title: \"Sample API\"\n  version: \"1.0.0\"\npaths: {}");
            this.fileHandler.createFile(projectDir, "pages.xml", "<pages>\n    <!-- Page configurations go here -->\n</pages>");
            this.fileHandler.createDirectory(projectDir, "styles/components");
            this.fileHandler.createDirectory(projectDir, "styles/pages");
            this.fileHandler.createDirectory(projectDir, "js");
            this.commandHandler.createReactApp(ProjectManager.projectName);
            this.commandHandler.installNpmPackage(ProjectManager.projectName, "react-router-dom");
            this.fileHandler.createDirectory(projectDir, "build/src/components");
            this.fileHandler.createDirectory(projectDir, "build/src/pages");
            this.fileHandler.createDirectory(projectDir, "build/src/customStyles");
            this.fileHandler.createDirectory(projectDir, "build/src/common");
            copyTemplateFiles();
            logger.info("Project initialized successfully.");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void runProject() {
        this.commandHandler.runReactApp(ProjectManager.projectName);
    }

    public void buildProject() {
        PagesFileHandler pagesFileHandler = new PagesFileHandler(ProjectManager.projectName);
        List<PageDTO> pageDTOs = pagesFileHandler.getPages();

        checkCustomStyleFiles(pageDTOs);
        generatePages(pageDTOs);
        copyUserFiles();
        createClientApi();
    }

    public void test() {
        PagesFileHandler pagesFileHandler = new PagesFileHandler(ProjectManager.projectName);
        List<Page> pages = pagesFileHandler.getPagesNew();

        generatePagesNew(pages);
        createClientApi();
    }
}
