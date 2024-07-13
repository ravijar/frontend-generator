package com.ravijar.handler;

import com.ravijar.core.ProjectManager;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OpenapiFileHandler {
    private static final Logger logger = LogManager.getLogger(OpenapiFileHandler.class);

    private final String openapiFilePath = ProjectManager.getProjectName() + "\\openapi.yaml";
    private final OpenAPI openAPIData;

    public OpenapiFileHandler() {
        this.openAPIData = getSpecData();
    }

    private OpenAPI getSpecData() {
        File openapiFile = new File(openapiFilePath);
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

    private Operation getOperation(String path, PathItem.HttpMethod method) {
        if (openAPIData == null) {
            logger.error("OpenAPI data is not initialized.");
            return null;
        }

        Map<String, PathItem> paths = openAPIData.getPaths();
        if (paths == null || !paths.containsKey(path)) {
            logger.error("Path not found in OpenAPI specification: {}", path);
            return null;
        }

        PathItem pathItem = paths.get(path);
        Operation operation = pathItem.readOperationsMap().get(method);
        if (operation == null) {
            logger.error("HTTP method not found for path in OpenAPI specification: {} {}", method, path);
            return null;
        }

        return operation;
    }

    private String getBaseUrl() {
        List<Server> servers = openAPIData.getServers();
        if (servers != null && !servers.isEmpty()) {
            return servers.get(0).getUrl();
        } else {
            logger.error("No servers found in OpenAPI specification.");
            return "";
        }
    }

    private Map<String, String> extractProperties(Map<String, Schema> openApiData) {
        Map<String, String> properties = new HashMap<>();

        Set<String> keys = openApiData.keySet();
        for (String key: keys) {
            Schema value = openApiData.get(key);
            properties.put(key, value.getTypes().iterator().next().toString());
        }

        return properties;
    }

    public List<Parameter> getParameters(String path, PathItem.HttpMethod method) {
        Operation operation = getOperation(path, method);

        if (operation == null) {
            return null;
        }

        return operation.getParameters();
    }

    public Map<String, String> getResponseSchema(String path, PathItem.HttpMethod method, String responseType) {
        Operation operation = getOperation(path, method);

        if (operation == null) {
            return null;
        }

        Map<String, ApiResponse> responses = operation.getResponses();
        if (responses == null || !responses.containsKey(responseType)) {
            logger.error("Response type not found for path and method in OpenAPI specification: {} {} {}", responseType, method, path);
            return null;
        }

        ApiResponse apiResponse = responses.get(responseType);
        if (apiResponse.getContent() == null || apiResponse.getContent().isEmpty()) {
            logger.error("No content found for response type: {} {} {}", responseType, method, path);
            return null;
        }

        return extractProperties(apiResponse.getContent().values().iterator().next().getSchema().getProperties());
    }

    public String getUrlEndpoint(String resourceUrl) {
        if (openAPIData == null) {
            logger.error("OpenAPI data is not initialized.");
            return null;
        }

        Map<String, PathItem> paths = openAPIData.getPaths();
        if (paths == null) {
            logger.error("No paths found in OpenAPI specification.");
            return null;
        }

        for (String path : paths.keySet()) {
            if (path.startsWith(resourceUrl)) {
                int paramIndex = path.indexOf("{");
                String basePath = paramIndex == -1 ? path : path.substring(0, paramIndex);
                String baseUrl = getBaseUrl();
                return baseUrl + basePath;
            }
        }

        logger.error("Resource URL not found in OpenAPI specification: {}", resourceUrl);
        return null;
    }


}
