package com.ravijar;

import com.ravijar.core.ProjectManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        String[] commands = {"help", "init", "build", "run", "test"};

        ProjectManager projectManager = new ProjectManager();

        if (args.length > 0) {
            switch (args[0]) {
                case "help":
                    logger.info("Supported commands:");
                    for (String command : commands) {
                        logger.info("- {}",command);
                    }
                    break;
                case "init":
                    if (args.length == 2) {
                        ProjectManager.setProjectName(args[1]);
                        projectManager.initializeProject();
                    } else {
                        projectManager.initializeProject();
                        logger.info("Project will be named as 'Untitled'.");
                    }
                    break;
                case "run":
                    logger.info("Starting application...");
                    projectManager.runProject();
                    break;
                case "build":
                    logger.info("Building application...");
                    projectManager.buildProject();
                    break;
                case "test":
                    logger.info("Running test...");
                    projectManager.test();
                    break;
                default:
                    logger.error("Invalid Command. Use 'help' to list supported commands.");
                    break;
            }
        } else {
            logger.error("No command provided. Use 'help' to list supported commands.");
        }
    }

}
