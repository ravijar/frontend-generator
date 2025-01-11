package com.ravijar.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppCLI {
    private static final Logger logger = LogManager.getLogger(AppCLI.class);

    public static void main(String[] args) {
        String[] commands = {"help", "init", "build", "build --api", "build --code", "build --styles", "run", "test"};
        String configNotFound = "Configuration file not found. Initialize a project first!";

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
                        projectManager.initializeProject(args[1]);
                    } else {
                        logger.error("Enter project name!");
                    }
                    break;
                case "run":
                    logger.info("Starting application...");
                    if(!projectManager.runProject()) logger.error(configNotFound);
                    break;
                case "build":
                    if (args.length == 2){
                        switch (args[1]){
                            case "--api":
                                if(!projectManager.generateClientAPI()) logger.error(configNotFound);
                                break;
                            case "--code":
                                if(!projectManager.generateCode()) logger.error(configNotFound);
                                break;
                            case "--styles":
                                if(!projectManager.addUserStyles()) logger.error(configNotFound);
                        }
                    } else {
                        logger.info("Building application...");
                        if(!projectManager.buildProject()) logger.error(configNotFound);
                    }
                    break;
                case "test":
                    logger.info("Running test...");
                    if(!projectManager.test()) logger.error(configNotFound);
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
