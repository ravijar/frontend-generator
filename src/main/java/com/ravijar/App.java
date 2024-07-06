package com.ravijar;

import com.ravijar.config.FreeMarkerConfig;
import com.ravijar.core.ReactCodeGenerator;
import com.ravijar.handler.OpenapiFileHandler;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        OpenapiFileHandler openapiFileHandler = new OpenapiFileHandler();
        System.out.println(openapiFileHandler.getSpecData().getInfo());

        FreeMarkerConfig freeMarkerConfig = new FreeMarkerConfig();

        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try {

            Configuration cfg = freeMarkerConfig.getConfiguration();
            ReactCodeGenerator codeGenerator = new ReactCodeGenerator(cfg);

            codeGenerator.generateReactComponent(outputDir.getAbsolutePath());

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
