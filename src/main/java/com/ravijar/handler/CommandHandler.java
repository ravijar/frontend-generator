package com.ravijar.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandHandler {
    private static final Logger logger = LogManager.getLogger(CommandHandler.class);

    private void runProcessBuilder(ProcessBuilder processBuilder) {
        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }

            int exitCode = process.waitFor();
            logger.info("Exited with error code : {}" ,exitCode);

        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    public void createReactApp(String baseDir) {

        File dir = new File(baseDir);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logger.info("Directory {} created.", baseDir);
            } else {
                logger.error("Failed to create directory {}.", baseDir);
                return;
            }
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("npx.cmd", "create-react-app", "build");
        processBuilder.directory(dir);
        runProcessBuilder(processBuilder);
    }

    public void runReactApp(String baseDir) {
        File dir = new File(baseDir, "build");
        if (!dir.exists()) {
            logger.error("The directory {} does not exist. Ensure the React app is created before running it.", dir.getAbsolutePath());
            return;
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("npm.cmd", "start");
        processBuilder.directory(dir);
        runProcessBuilder(processBuilder);
    }
}
