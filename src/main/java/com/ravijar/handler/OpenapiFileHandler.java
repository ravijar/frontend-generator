package com.ravijar.handler;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.io.File;

public class OpenapiFileHandler {
    String openapiFilePath = "input\\openapi.yaml";
    File openapiFile = new File(openapiFilePath);

    public OpenAPI getSpecData() {

        if (!openapiFile.exists() || !openapiFile.canRead()) {
            System.err.println("File not found or not readable: " + openapiFile.getAbsolutePath());

            return null;
        }

        OpenAPIV3Parser parser = new OpenAPIV3Parser();
        SwaggerParseResult result = parser.readLocation(openapiFile.getAbsolutePath(), null, null);

        if (result.getMessages().isEmpty()) {
            System.out.println("OpenAPI specification parsed successfully.");
            System.out.println("OpenAPI version: " + result.getOpenAPI().getOpenapi());

            return result.getOpenAPI();
        } else {
            System.err.println("Failed to parse OpenAPI specification:");
            result.getMessages().forEach(System.err::println);

            return null;
        }
    }

}
