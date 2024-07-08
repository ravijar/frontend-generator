package com.ravijar.core;

import com.ravijar.handler.CommandHandler;
import com.ravijar.handler.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class ProjectManager {
    private static final Logger logger = LogManager.getLogger(ProjectManager.class);

    private String projectName = "Untitled";

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void initializeProject() {

        FileHandler fileHandler = new FileHandler();
        CommandHandler commandHandler = new CommandHandler();

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
            fileHandler.createFile(projectDir, "openapi.yaml", "# OpenAPI specification\nopenapi: \"3.0.0\"\ninfo:\n  title: \"Sample API\"\n  version: \"1.0.0\"\npaths: {}");
            fileHandler.createFile(projectDir, "pages.xml", "<pages>\n    <!-- Page configurations go here -->\n</pages>");
            fileHandler.createDirectory(projectDir, "css");
            fileHandler.createDirectory(projectDir, "js");
            commandHandler.createReactApp(this.projectName);
            logger.info("Project initialized successfully.");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
