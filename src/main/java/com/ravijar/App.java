package com.ravijar;

import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.io.File;

public class App {

    public static void main(String[] args) {
        String yamlFilePath = "src\\main\\java\\com\\ravijar\\openapi.yaml";

        File yamlFile = new File(yamlFilePath);
        System.out.println("Looking for file at: " + yamlFile.getAbsolutePath());

        if (!yamlFile.exists() || !yamlFile.canRead()) {
            System.err.println("File not found or not readable: " + yamlFile.getAbsolutePath());
            return;
        }

        OpenAPIV3Parser parser = new OpenAPIV3Parser();
        SwaggerParseResult result = parser.readLocation(yamlFile.getAbsolutePath(), null, null);

        if (result.getMessages().isEmpty()) {
            System.out.println("OpenAPI specification parsed successfully.");
            System.out.println("OpenAPI version: " + result.getOpenAPI().getOpenapi());

        } else {
            System.err.println("Failed to parse OpenAPI specification:");
            result.getMessages().forEach(System.err::println);
        }
    }
}
