package com.ravijar;

import com.ravijar.config.FreeMarkerConfig;
import com.ravijar.core.ReactCodeGenerator;
import com.ravijar.handler.OpenapiFileHandler;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class App {

    public static void main(String[] args) {
//        OpenapiFileHandler openapiFileHandler = new OpenapiFileHandler();
//        System.out.println(openapiFileHandler.getSpecData().getInfo());
//
//        FreeMarkerConfig freeMarkerConfig = new FreeMarkerConfig();
//
//        File outputDir = new File("output");
//        if (!outputDir.exists()) {
//            outputDir.mkdirs();
//        }
//
//        try {
//
//            Configuration cfg = freeMarkerConfig.getConfiguration();
//            ReactCodeGenerator codeGenerator = new ReactCodeGenerator(cfg);
//
//            codeGenerator.generateReactComponent(outputDir.getAbsolutePath());
//
//        } catch (IOException | TemplateException e) {
//            e.printStackTrace();
//        }

        if (args.length > 0 && "create-app".equals(args[0])) {
            createReactApp();
        } else {
            System.out.println("Invalid command. Use 'create-app' to create a React application.");
        }
    }

    private static void createReactApp() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("C:\\Program Files\\nodejs\\npx.cmd", "create-react-app", "output");



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
