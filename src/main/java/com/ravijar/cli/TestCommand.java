package com.ravijar.cli;

import com.ravijar.core.ProjectManager;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "init", description = "Initializes a new project.")
public class TestCommand implements Runnable {
    private final ProjectManager projectManager;

    public TestCommand(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public void run() {
        System.out.println("Testing...");
//        projectManager.test();
    }
}
