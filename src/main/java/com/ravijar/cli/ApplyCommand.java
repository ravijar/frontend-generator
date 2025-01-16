package com.ravijar.cli;

import com.ravijar.core.ProjectManager;
import picocli.CommandLine.Command;

@Command(name = "apply", description = "Applies User Customizations.")
public class ApplyCommand implements Runnable {

    private final ProjectManager projectManager;

    public ApplyCommand(ProjectManager projectManager){
        this.projectManager=projectManager;
    }

    @Command(name = "--styles", description = "Applies User Styles.")
    public void applyStyles() {
        projectManager.applyUserStyles();
    }

    @Override
    public void run() {}
}
