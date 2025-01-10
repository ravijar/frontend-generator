package com.ravijar.cli;
import com.ravijar.core.ProjectManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "init", description = "Initializes a new project.")
public class InitCommand implements Runnable {
    private final ProjectManager projectManager;

    public InitCommand(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Option(names = {"-n", "--name"}, description = "Specify the project name", defaultValue = "Untitled")
    private String projectName;

    @Override
    public void run() {
        System.out.println("Initializing a new project with template: " + projectName);
        ProjectManager.setProjectName(projectName);
        projectManager.initializeProject();
    }
}
