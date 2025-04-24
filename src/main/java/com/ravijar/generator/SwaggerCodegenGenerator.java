package com.ravijar.generator;

import com.ravijar.handler.CommandHandler;
import io.swagger.codegen.v3.cli.SwaggerCodegen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Deprecated
public class SwaggerCodegenGenerator {
    private static final Logger logger = LogManager.getLogger(SwaggerCodegenGenerator.class);

    public void generateClientApi(File specDir, File outputDir, String language) {
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String[] codegenArgs = new String[] {
                "generate",
                "-i", specDir.getAbsolutePath(),
                "-l", language,
                "-o", outputDir.getAbsolutePath()
        };

        SwaggerCodegen.main(codegenArgs);

        File packageJson = new File(outputDir, "package.json");

        while (!packageJson.exists()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }

        CommandHandler commandHandler = CommandHandler.getCommandHandler();
        commandHandler.npmInstall(outputDir);
    }
}
