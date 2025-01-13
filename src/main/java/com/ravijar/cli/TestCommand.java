package com.ravijar.cli;

import com.ravijar.core.ProjectManager;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "test", description = "Test the project.")
public class TestCommand implements Runnable {
    private final ProjectManager projectManager;

    public TestCommand(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public void run() {
        projectManager.test();
    }
}
