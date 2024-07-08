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
    private final FileHandler fileHandler;
    private final CommandHandler commandHandler;

    public ProjectManager() {
        this.fileHandler = new FileHandler();
        this.commandHandler = new CommandHandler();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
            this.fileHandler.createDirectory(projectDir, "css");
            this.fileHandler.createDirectory(projectDir, "js");
            this.commandHandler.createReactApp(this.projectName);
            logger.info("Project initialized successfully.");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void runProject() {
        this.commandHandler.runReactApp(this.projectName);
    }
}
