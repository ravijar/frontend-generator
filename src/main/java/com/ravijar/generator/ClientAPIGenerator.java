package com.ravijar.generator;

import com.ravijar.handler.CommandHandler;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ClientAPIGenerator {
    private static final Logger logger = LogManager.getLogger(ClientAPIGenerator.class);

    public void generateClientAPI(File specFile, File outputDir, String language) {
        if (!specFile.exists()) {
            logger.error("OpenAPI spec file not found: {}", specFile.getAbsolutePath());
            return;
        }

        if (!outputDir.exists()) {
            if (outputDir.mkdirs()) {
                logger.info("Created output directory: {}", outputDir.getAbsolutePath());
            } else {
                logger.error("Failed to create output directory: {}", outputDir.getAbsolutePath());
                return;
            }
        }

        try {
            CodegenConfigurator configurator = new CodegenConfigurator()
                    .setInputSpec(specFile.toURI().toString())
                    .setGeneratorName(language)
                    .setOutputDir(outputDir.getAbsolutePath());

            DefaultGenerator generator = new DefaultGenerator();
            generator.opts(configurator.toClientOptInput()).generate();

            CommandHandler commandHandler = new CommandHandler();
            logger.info("Installing client API dependencies...");
            commandHandler.npmInstall(outputDir);
            logger.info("Client API dependencies installed!");

            logger.info("Client API successfully generated for language: {}", language);
        } catch (Exception e) {
            logger.error("Error during client API generation: {}", e.getMessage(), e);
        }
    }
}

