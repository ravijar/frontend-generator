package com.ravijar.cli;

import com.ravijar.core.ProjectManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "build", description = "Builds the project.")
public class BuildCommand implements Runnable {

    private final ProjectManager projectManager;

    public BuildCommand(ProjectManager projectManager){
        this.projectManager=projectManager;
    }

    @Override
    public void run() {
        System.out.println("Applying User Styles... ");
//        projectManager.addUserStyles();
    }
}
