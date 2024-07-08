package com.ravijar.core;

import com.ravijar.handler.CommandHandler;
import com.ravijar.handler.FileHandler;

import java.io.File;
import java.io.IOException;

public class ProjectManager {

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
                System.out.println("Project directory " + projectName + " created.");
            } else {
                System.out.println("Failed to create project directory " + projectName + ".");
                return;
            }
        } else {
            System.out.println("Project directory " + projectName + " already exists.");
        }

        try {
            fileHandler.createFile(projectDir, "openapi.yaml", "# OpenAPI specification\nopenapi: \"3.0.0\"\ninfo:\n  title: \"Sample API\"\n  version: \"1.0.0\"\npaths: {}");
            fileHandler.createFile(projectDir, "pages.xml", "<pages>\n    <!-- Page configurations go here -->\n</pages>");
            fileHandler.createDirectory(projectDir, "css");
            fileHandler.createDirectory(projectDir, "js");
            commandHandler.createReactApp(this.projectName);
            System.out.println("Project initialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
