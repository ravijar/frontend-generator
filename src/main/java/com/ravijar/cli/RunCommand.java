package com.ravijar.cli;

import com.ravijar.core.ProjectManager;
import picocli.CommandLine.Command;

@Command(name = "run", description = "Run the project.")
public class RunCommand implements Runnable {
    private final ProjectManager projectManager;

    public RunCommand(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public void run() {
        System.out.println("Running the project...");
//        projectManager.runProject();
    }
}
