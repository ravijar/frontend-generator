package com.ravijar.parser;

import com.ravijar.helper.OpenAPIConverter;
import com.ravijar.model.PageDTO;
import com.ravijar.model.openapi.OpenAPIParameter;
import com.ravijar.model.openapi.OpenAPIResource;
import com.ravijar.model.openapi.OpenAPIResponse;
import com.ravijar.model.openapi.OpenAPISchemaProperty;
import com.ravijar.model.xml.Resource;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class OpenAPIParser {
    private static final Logger logger = LogManager.getLogger(OpenAPIParser.class);

    private final String openapiFilePath;
    private final OpenAPI openAPIData;

    public OpenAPIParser(String filePath) {
        this.openapiFilePath = filePath;
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

    private ApiResponse getApiResponse(String path, PathItem.HttpMethod method, String responseType) {
        Operation operation = getOperation(path, method);

        if (operation == null) {
            return null;
        }

        Map<String, ApiResponse> responses = operation.getResponses();
        if (responses == null || !responses.containsKey(responseType)) {
            logger.error("Response not found for path in OpenAPI specification: {} {}", path, method);
            return null;
        }

        return responses.get(responseType);
    }

    private String getSchemaFromRef(String ref) {
        return ref.substring(ref.lastIndexOf('/') + 1);
    }

    private Schema getResponseSchema(String path, PathItem.HttpMethod method, String responseType) {
        ApiResponse apiResponse = getApiResponse(path, method, responseType);
        if (apiResponse == null) {
            return null;
        }

        if (apiResponse.getContent() == null || apiResponse.getContent().isEmpty()) {
            logger.error("Response content not found for path in OpenAPI specification: {} {} {}", path, method, responseType);
            return null;
        }

        return apiResponse.getContent().values().iterator().next().getSchema();
    }

    private List<OpenAPISchemaProperty> extractProperties(Map<String, Schema> openApiData) {
        List<OpenAPISchemaProperty> properties = new ArrayList<>();

        Set<String> keys = openApiData.keySet();
        for (String key: keys) {
            Schema value = openApiData.get(key);
            String displayName = getExtentionString(value.getExtensions(), "x-displayName");
            if (value.getTypes() != null) {
                properties.add(new OpenAPISchemaProperty(key, value.getTypes().iterator().next().toString(), displayName));
            } else if (value.get$ref() != null) {
                properties.add(new OpenAPISchemaProperty(key, getSchemaFromRef(value.get$ref()), displayName));
            } else {
                logger.error("Type not defined properly for the property {}.", key);
            }
        }

        return properties;
    }

    public String getExtentionString(Map<String, Object> extensions, String extensionName) {
        if (extensions != null) {
            Object result = extensions.get(extensionName);
            if (result instanceof String) {
                return result.toString();
            }
            logger.warn("Extension {} is not a string.", extensionName);
        }
        logger.warn("No extensions provided.");
        return null;
    }

    public List<OpenAPIParameter> getParameters(String path, PathItem.HttpMethod method) {
        Operation operation = getOperation(path, method);

        if (operation == null) {
            return null;
        }

        List<Parameter> parameters = operation.getParameters();
        List<OpenAPIParameter> openAPIParameters = new ArrayList<>();

        if (parameters == null) {
            return openAPIParameters;
        }

        for (Parameter parameter : parameters) {
            openAPIParameters.add(new OpenAPIParameter(parameter.getName(), getExtentionString(parameter.getExtensions(), "x-displayName")));
        }

        return openAPIParameters;
    }

    public String getRequestSchema(String path, PathItem.HttpMethod method) {
        Operation operation = getOperation(path, method);

        if (operation == null) {
            return null;
        }

        if (operation.getRequestBody() == null) {
            logger.error("No request body found for path and method: {} {}", method, path);
            return null;
        }

        Map<String, MediaType> content = operation.getRequestBody().getContent();
        if (content == null || content.isEmpty()) {
            logger.error("No content found for request body: {} {}", method, path);
            return null;
        }

        return getSchemaFromRef(content.values().iterator().next().getSchema().get$ref());
    }

    public String getResponseDescription(String path, PathItem.HttpMethod method, String responseType) {
        ApiResponse apiResponse = getApiResponse(path, method, responseType);
        if (apiResponse == null) {
            return null;
        }

        return apiResponse.getDescription();
    }

    public String getResponseSchemaName(String path, PathItem.HttpMethod method, String responseType) {
        Schema schema = getResponseSchema(path, method, responseType);
        if(schema == null) {
            logger.error("No Content found for response type: {} {} {}", responseType, method, path);
            return null;
        }
        String ref = schema.get$ref();
        if(ref == null) {
            Schema itemSchema = schema.getItems();
            if(itemSchema != null) {
                ref = itemSchema.get$ref();
                if(ref != null) {
                    return getSchemaFromRef(ref);
                }
            }
            logger.error("No Ref found for response type: {} {} {}", responseType, method, path);
            return null;
        }
        return getSchemaFromRef(ref);
    }

    public String getResponseSchemaType(String path, PathItem.HttpMethod method, String responseType) {
        Schema schema = getResponseSchema(path, method, responseType);
        if(schema == null) {
            logger.error("No content found for response type: {} {} {}", responseType, method, path);
            return null;
        }
        if(schema.getTypes() == null) {
            return "null";
        }
        return schema.getTypes().iterator().next().toString();
    }

    public Map<String, List<OpenAPISchemaProperty>> getSchemas() {
        if (openAPIData == null) {
            logger.error("OpenAPI data is not initialized.");
            return null;
        }

        Map<String, Schema> result = openAPIData.getComponents().getSchemas();
        Map<String, List<OpenAPISchemaProperty>> schemas = new HashMap<>();

        for (String key : result.keySet()) {
            List<OpenAPISchemaProperty> schema = extractProperties(result.get(key).getProperties());
            schemas.put(key, schema);
        }

        if (schemas == null || schemas.isEmpty()) {
            logger.error("No schemas found in OpenAPI specification.");
            return null;
        }

        return schemas;
    }

    public String getOperationId(String path, PathItem.HttpMethod method) {
        Operation operation = getOperation(path, method);
        if (operation == null) {
            return null;
        }
        return operation.getOperationId();
    }

    public Set<String> getResponseCodes(String path, PathItem.HttpMethod method) {
        Operation operation = getOperation(path, method);

        if (operation == null) {
            logger.error("Operation not found for path: {} and method: {}", path, method);
            return Collections.emptySet();
        }

        Map<String, ApiResponse> responses = operation.getResponses();

        if (responses == null || responses.isEmpty()) {
            logger.error("No responses found for path: {} and method: {}", path, method);
            return Collections.emptySet();
        }

        return responses.keySet();
    }

    public OpenAPIResource getResourceData(Resource resource) {
        PathItem.HttpMethod httpMethod = OpenAPIConverter.getHttpMethod(resource.getMethod());
        String url = resource.getUrl();

        String apiFunctionName = getOperationId(url, httpMethod);
        List<OpenAPIParameter> urlParameters = getParameters(url, httpMethod);
        String requestSchema = getRequestSchema(url, httpMethod);
        Set<String> responseCodes = getResponseCodes(url, httpMethod);

        List<OpenAPISchemaProperty> requestParameters;
        requestParameters = getSchemas().get(requestSchema);
        if (requestParameters == null) {
            requestParameters = new ArrayList<>();
        }

        // Remove any requestParameter with the same name as a urlParameter
        Set<String> urlParameterNames = urlParameters.stream().map(OpenAPIParameter::getName).collect(Collectors.toSet());
        requestParameters.removeIf(param -> urlParameterNames.contains(param.getName()));

        List<OpenAPIResponse> responses = new ArrayList<>();
        for (String code : responseCodes) {
            String schemaName = getResponseSchemaName(url, httpMethod, code);
            String type = getResponseSchemaType(url, httpMethod, code);
            String description = getResponseDescription(url, httpMethod, code);
            responses.add(new OpenAPIResponse(code, schemaName, type, description, getSchemas().get(schemaName)));
        }

        return new OpenAPIResource(resource.getMethod(), apiFunctionName, urlParameters, requestParameters, responses);
    }

    @Deprecated
    private String getBaseUrl() {
        List<Server> servers = openAPIData.getServers();
        if (servers != null && !servers.isEmpty()) {
            return servers.get(0).getUrl();
        } else {
            logger.error("No servers found in OpenAPI specification.");
            return "";
        }
    }

    @Deprecated
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

    @Deprecated
    public List<String> getNextPages(String path, PathItem.HttpMethod method, String responseType) {
        Operation operation = getOperation(path, method);

        if (operation == null) {
            return Collections.emptyList();
        }

        Map<String, ApiResponse> responses = operation.getResponses();
        if (responses == null || !responses.containsKey(responseType)) {
            logger.error("Response type not found for path and method in OpenAPI specification: {} {} {}", responseType, method, path);
            return Collections.emptyList();
        }

        ApiResponse apiResponse = responses.get(responseType);
        Map<String, Object> extensions = apiResponse.getExtensions();

        if (extensions == null || !extensions.containsKey("x-nextPages")) {
            logger.debug("No next pages found for path and method in OpenAPI specification: {} {} {}", method, path, responseType);
            return Collections.emptyList();
        }

        Object nextPagesObject = extensions.get("x-nextPages");
        if (nextPagesObject instanceof List) {
            List<?> nextPagesList = (List<?>) nextPagesObject;
            List<String> nextPages = new ArrayList<>();
            for (Object nextPage : nextPagesList) {
                if (nextPage instanceof String) {
                    nextPages.add((String) nextPage);
                }
            }
            return nextPages;
        }

        return Collections.emptyList();
    }

    @Deprecated
    public void getPageExtensions(PageDTO pageDTO) {
        Operation operation = getOperation(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        pageDTO.setPageTitle(getExtentionString(operation.getExtensions(), "x-pageTitle"));
    }

}
