package com.ravijar.core;

import io.swagger.codegen.v3.cli.SwaggerCodegen;

import java.io.File;

public class SwaggerCodegenGenerator {

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
    }
}
