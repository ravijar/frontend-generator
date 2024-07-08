package com.ravijar.handler;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class OpenapiFileHandler {
    private static final Logger logger = LogManager.getLogger(OpenapiFileHandler.class);


    String openapiFilePath = "input\\openapi.yaml";
    File openapiFile = new File(openapiFilePath);

    public OpenAPI getSpecData() {

        if (!openapiFile.exists() || !openapiFile.canRead()) {
            logger.error("File not found or not readable: {}", openapiFile.getAbsolutePath());
            return null;
        }

        OpenAPIV3Parser parser = new OpenAPIV3Parser();
        SwaggerParseResult result = parser.readLocation(openapiFile.getAbsolutePath(), null, null);

        if (result.getMessages().isEmpty()) {
            logger.info("OpenAPI specification parsed successfully.");
            logger.info("OpenAPI specification version: {}", result.getOpenAPI().getOpenapi());
            return result.getOpenAPI();

        } else {
            logger.error("Failed to parse OpenAPI specification:");
            return null;

        }
    }

}
