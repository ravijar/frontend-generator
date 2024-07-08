package com.ravijar.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandHandler {

    private final String npx = "C:\\Program Files\\nodejs\\npx.cmd";

    public void createReactApp(String baseDir) {

        File dir = new File(baseDir);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Directory " + baseDir + " created.");
            } else {
                System.out.println("Failed to create directory " + baseDir + ".");
                return;
            }
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(npx, "create-react-app", "build");
        processBuilder.directory(dir);

        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
