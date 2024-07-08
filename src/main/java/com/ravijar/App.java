package com.ravijar;

import com.ravijar.core.ProjectManager;

public class App {

    public static void main(String[] args) {
        String[] commands = {"init"};

        ProjectManager projectManager = new ProjectManager();

        if (args.length > 0) {
            switch (args[0]) {
                case "help":
                    for (String command : commands) {
                        System.out.println("- " + command);
                    }
                case "init":
                    if (args.length == 2) {
                        projectManager.setProjectName(args[1]);
                        projectManager.initializeProject();
                    } else {
                        projectManager.initializeProject();
                        System.out.println("Project will be named as 'Untitled'.");
                    }
                    break;
                default:
                    System.out.println("Invalid command. Use 'help' to list supported commands.");
                    break;
            }
        } else {
            System.out.println("No command provided. Use 'help' to list supported commands..");
        }
    }

}
