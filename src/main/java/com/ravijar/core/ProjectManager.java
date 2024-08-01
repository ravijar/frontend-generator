package com.ravijar.core;

import com.ravijar.config.FreeMarkerConfig;
import com.ravijar.handler.CommandHandler;
import com.ravijar.handler.FileHandler;
import com.ravijar.handler.OpenapiFileHandler;
import com.ravijar.handler.PagesFileHandler;
import com.ravijar.model.Page;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
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
    private final String templatesDir = "src\\main\\resources\\templates\\";
    private final String[] reactComponentTemplates = {"InputField", "KeyValuePair", "RecursiveKeyValuePair"};
    private final String[] cssComponentTemplates = {"InputField", "KeyValuePair", "RecursiveKeyValuePair"};
    private final String[] cssPageTemplates = {"Page"};

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
        String cssDir = ProjectManager.projectName + "\\css\\";

        for (String reactTemplate : reactComponentTemplates) {
            File sourceFile = new File(templatesDir + "react\\components\\" + reactTemplate + ".js");
            File destFile = new File(buildSrcDir + "components\\" + reactTemplate + ".js");
            fileHandler.copyFile(sourceFile,destFile);
        }

        for (String cssTemplate : cssComponentTemplates) {
            File sourceFile = new File(templatesDir + "css\\components\\" + cssTemplate + ".css");
            fileHandler.copyFile(sourceFile, new File(buildSrcDir + "components\\" + cssTemplate + ".css"));
            fileHandler.copyFile(sourceFile, new File( cssDir + "components\\" + cssTemplate + ".css"));
        }

        for (String cssTemplate : cssPageTemplates) {
            File sourceFile = new File(templatesDir + "css\\pages\\" + cssTemplate + ".css");
            fileHandler.copyFile(sourceFile, new File(buildSrcDir + "pages\\" + cssTemplate + ".css"));
            fileHandler.copyFile(sourceFile, new File(cssDir + "pages\\" + cssTemplate + ".css"));
        }
    }

    private void createClientApi() {
        SwaggerCodegenGenerator swaggerCodegenGenerator = new SwaggerCodegenGenerator();
        File specDir = new File(projectName + "\\openapi.yaml");
        File outputDir = new File(projectName + "\\build\\src\\client_api");
        swaggerCodegenGenerator.generateClientApi(specDir, outputDir, "javascript");
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
            this.fileHandler.createDirectory(projectDir, "css/components");
            this.fileHandler.createDirectory(projectDir, "css/pages");
            this.fileHandler.createDirectory(projectDir, "js");
            this.commandHandler.createReactApp(ProjectManager.projectName);
            this.commandHandler.installNpmPackage(ProjectManager.projectName, "react-router-dom");
            this.fileHandler.createDirectory(projectDir, "build/src/components");
            this.fileHandler.createDirectory(projectDir, "build/src/pages");
            this.fileHandler.createDirectory(projectDir, "build/src/models");
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
        String buildSrcDir = ProjectManager.projectName + "\\build\\src\\";
        String cssDir = ProjectManager.projectName + "\\css\\";
        FreeMarkerConfig freeMarkerConfig = new FreeMarkerConfig();
        PagesFileHandler pagesFileHandler = new PagesFileHandler(ProjectManager.projectName);
        OpenapiFileHandler openapiFileHandler = new OpenapiFileHandler();
        List<Page> pages = pagesFileHandler.getPages();

        File pageOutputDir = new File(ProjectManager.projectName + "\\build\\src\\pages");
        if (!pageOutputDir.exists()) {
            pageOutputDir.mkdirs();
        }

        File appOutputDir = new File(ProjectManager.projectName + "\\build\\src");
        if (!appOutputDir.exists()) {
            appOutputDir.mkdirs();
        }

        File modelsDir = new File(ProjectManager.projectName + "\\build\\src\\models");
        if (!modelsDir.exists()) {
            modelsDir.mkdirs();
        }

        try {

            Configuration cfg = freeMarkerConfig.getConfiguration();
            ReactCodeGenerator codeGenerator = new ReactCodeGenerator(cfg);

            for (Page page: pages) {
                codeGenerator.createPage(pageOutputDir.getAbsolutePath(), page);
            }

            codeGenerator.updateAppPage(appOutputDir.getAbsolutePath(), pages);
            codeGenerator.generateModels(modelsDir.getAbsolutePath(), openapiFileHandler.getSchemas());

        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage());
        }

        for (String cssTemplate : cssComponentTemplates) {
            fileHandler.copyFile(
                    new File(cssDir + "components\\" + cssTemplate + ".css"),
                    new File(buildSrcDir + "components\\" + cssTemplate + ".css")
            );
        }

        for (String cssTemplate : cssPageTemplates) {
            fileHandler.copyFile(
                    new File(cssDir + "pages\\" + cssTemplate + ".css"),
                    new File(buildSrcDir + "pages\\" + cssTemplate + ".css")
            );
        }

        createClientApi();
    }

    public void test() {

    }
}
