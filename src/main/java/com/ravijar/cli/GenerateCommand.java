package com.ravijar.cli;

import com.ravijar.core.ProjectManager;
import picocli.CommandLine.Command;

@Command(name = "generate", description = "Generate frontend and related code.")
public class GenerateCommand implements Runnable {

    private final ProjectManager projectManager;

    public GenerateCommand(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Command(name = "--api", description = "Generates Client API using the OpenAPI Spec")
    public void generateApi() {
        projectManager.generateClientAPI();
    }

    @Command(name = "--frontend", description = "Generates Frontend Application")
    public void generateFrontend() {
        projectManager.generateFrontend();
    }

    @Override
    public void run() {
        projectManager.generateAll();
    }
}
