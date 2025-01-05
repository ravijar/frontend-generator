package com.ravijar.core;

import com.ravijar.handler.OpenapiFileHandler;
import com.ravijar.helper.StringConverter;
import com.ravijar.model.*;
import com.ravijar.model.freemarker.FreeMarkerPage;
import com.ravijar.model.openapi.OpenAPIResource;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.openapi.OpenAPIResponse;
import com.ravijar.model.xml.Page;
import com.ravijar.model.xml.Resource;
import com.ravijar.model.xml.component.Component;
import com.ravijar.model.xml.component.Container;
import com.ravijar.model.xml.component.Form;
import com.ravijar.model.xml.component.SearchBar;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.models.PathItem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class ReactCodeGenerator {
    private final Configuration cfg;
    private final OpenapiFileHandler openapiFileHandler;
    private final Map<String, List<SchemaPropertyDTO>> schemas;

    public ReactCodeGenerator(Configuration cfg) {
        this.cfg = cfg;
        this.openapiFileHandler = new OpenapiFileHandler();
        this.schemas = openapiFileHandler.getSchemas();
    }

    private PathItem.HttpMethod getHttpMethod(String method) {
        for (PathItem.HttpMethod httpMethod : PathItem.HttpMethod.values()) {
            if (httpMethod.name().equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        return null;
    }

    private OpenAPIResource getResourceData(Resource resource) {
        PathItem.HttpMethod httpMethod = getHttpMethod(resource.getMethod());
        String url = resource.getUrl();

        String apiFunctionName = openapiFileHandler.getOperationId(url, httpMethod);
        List<ParameterDTO> urlParameterList = openapiFileHandler.getParameters(url, httpMethod);
        String requestSchema = openapiFileHandler.getRequestSchema(url, httpMethod);
        Set<String> responseCodes = openapiFileHandler.getResponseCodes(url, httpMethod);

        List<String> urlParameters = new ArrayList<>();
        for (ParameterDTO parameter : urlParameterList) {
            urlParameters.add(parameter.getName());
        }

        List<String> requestParameters = new ArrayList<>();
        if (requestSchema != null) {
            for (SchemaPropertyDTO schemaPropertyDTO: schemas.get(requestSchema)) {
                requestParameters.add(schemaPropertyDTO.getName());
            }
        }

        List<OpenAPIResponse> responses = new ArrayList<>();
        for (String code : responseCodes) {
            String schema = openapiFileHandler.getResponseSchemaName(url, httpMethod, code);
            String type = openapiFileHandler.getResponseSchemaType(url, httpMethod, code);
            responses.add(new OpenAPIResponse(code, schema, type));
        }

        return new OpenAPIResource(resource.getMethod(), apiFunctionName, urlParameters, requestParameters, responses);
    }

    public void updateAppPage(String outputDir, List<PageDTO> pageDTOList) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        List<Map<String, String>> pages = new ArrayList<>();
        for (PageDTO pageDTO : pageDTOList) {
            Map<String, String> pageData = new HashMap<>();
            pageData.put("name", pageDTO.getPageName());
            pages.add(pageData);
        }
        dataModel.put("pages", pages);

        Template template = cfg.getTemplate("App.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/App.jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void createPage(String outputDir, PageDTO pageDTO) throws IOException, TemplateException {
        List<ParameterDTO> parameters = openapiFileHandler.getParameters(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        String requestSchema = openapiFileHandler.getRequestSchema(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        openapiFileHandler.getPageExtensions(pageDTO);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("apiMethod", openapiFileHandler.getOperationId(pageDTO.getResourceUrl(), pageDTO.getResourceMethod()));
        dataModel.put("requestSchema", requestSchema);
        dataModel.put("pageDTO",pageDTO);

        Set<String> responseCodes = openapiFileHandler.getResponseCodes(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        List<Map<String, String>> displayNames = new ArrayList<>();
        List<ResponseDTO> responses= new ArrayList<>();
        for (String code : responseCodes) {
            String responseSchemaName = openapiFileHandler.getResponseSchemaName(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code);
            String responseSchemaType = openapiFileHandler.getResponseSchemaType(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code);
            String responseDescription = openapiFileHandler.getResponseDescription(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code);
            List<String> nextPageList = openapiFileHandler.getNextPages(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code);

            List<SchemaPropertyDTO> result = schemas.get(responseSchemaName);
            if (result != null) {
                for (SchemaPropertyDTO schemaPropertyDTO : result) {
                    Map<String, String> displayName = new HashMap<>();
                    if (schemaPropertyDTO.getDisplayName() != null) {
                        displayName.put(schemaPropertyDTO.getName(), schemaPropertyDTO.getDisplayName());
                        if (!displayNames.contains(displayName)) {
                            displayNames.add(displayName);
                        }
                    }
                }
            }

            ResponseDTO responseDTO = new ResponseDTO(code, responseDescription, responseSchemaName, responseSchemaType, nextPageList);
            responses.add(responseDTO);
        }
        dataModel.put("responses", responses);

        List<Map<String, String>> fields = new ArrayList<>();
        List<Map<String, String>> requestParams = new ArrayList<>();
        for (ParameterDTO parameter : parameters) {
            Map<String, String> field = new HashMap<>();
            field.put("name", parameter.getName());
            if (!fields.contains(field)) {
                fields.add(field);
            }

            if (parameter.getDisplayName() != null) {
                Map<String, String> displayName = new HashMap<>();
                displayName.put(parameter.getName(), parameter.getDisplayName());
                if (!displayNames.contains(displayName)) {
                    displayNames.add(displayName);
                }
            }
        }

        if (requestSchema != null) {
            for (SchemaPropertyDTO schemaPropertyDTO : schemas.get(requestSchema)) {
                Map<String, String> field = new HashMap<>();
                field.put("name", schemaPropertyDTO.getName());
                if (!fields.contains(field)) {
                    fields.add(field);
                }

                if (schemaPropertyDTO.getDisplayName() != null) {
                    Map<String, String> displayName = new HashMap<>();
                    displayName.put(schemaPropertyDTO.getName(), schemaPropertyDTO.getDisplayName());
                    if (!displayNames.contains(displayName)) {
                        displayNames.add(displayName);
                    }
                }

                requestParams.add(field);
            }
        }
        dataModel.put("fields", fields);
        dataModel.put("requestParams", requestParams);
        dataModel.put("displayNames", displayNames);

        Template template = cfg.getTemplate("Page.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + pageDTO.getPageName() + ".jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void updateAppPageNew(String outputDir, List<Page> pages) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        List<FreeMarkerPage> freeMarkerPages = new ArrayList<>();
        for (Page page : pages) {
            freeMarkerPages.add(new FreeMarkerPage(page.getName(), page.getRoute(), null));
        }
        dataModel.put("data", freeMarkerPages);

        Template template = cfg.getTemplate("pages/App.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/App.jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void createNavBar(String outputDir, List<Page> pages) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        List<FreeMarkerPage> freeMarkerPages = new ArrayList<>();
        for (Page page : pages) {
            if (page.isNavbar()) {
                freeMarkerPages.add(new FreeMarkerPage(page.getName(), page.getRoute(), null));
            }
        }
        dataModel.put("data", freeMarkerPages);

        Template template = cfg.getTemplate("components/NavBar.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/NavBar.jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void createPageNew(String outputDir, Page page) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        List<FreeMarkerComponent> freeMarkerComponents = new ArrayList<>();

        for (Component component : page.getComponents()) {
            FreeMarkerComponent freeMarkerComponent = null;
            Resource resource = null;
            switch (component.getType()) {
                case "HeroSection", "Button":
                    freeMarkerComponent = new FreeMarkerComponent(
                            component.getId(),
                            StringConverter.toKebabCase(component.getId()),
                            component,
                            null
                    );
                    break;
                case "SearchBar":
                    resource = ((SearchBar) component).getResource();
                    freeMarkerComponent = new FreeMarkerComponent(
                            component.getId(),
                            StringConverter.toKebabCase(component.getId()),
                            component,
                            getResourceData(resource)
                    );
                    break;
                case "Form":
                    resource = ((Form) component).getResource();
                    freeMarkerComponent = new FreeMarkerComponent(
                            component.getId(),
                            StringConverter.toKebabCase(component.getId()),
                            component,
                            getResourceData(resource)
                    );
                    break;
                case "Container":
                    resource = ((Container) component).getResource();
                    freeMarkerComponent = new FreeMarkerComponent(
                            component.getId(),
                            StringConverter.toKebabCase(component.getId()),
                            component,
                            getResourceData(resource));
                    break;
            }
            freeMarkerComponents.add(freeMarkerComponent);
        }

        FreeMarkerPage freeMarkerPage = new FreeMarkerPage(page.getName(), page.getRoute(), freeMarkerComponents);
        dataModel.put("data", freeMarkerPage);

        Template template = cfg.getTemplate("pages/Page.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + page.getName() + ".jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    @Deprecated
    public void generateModels(String outputDir) throws IOException, TemplateException {
        for (Map.Entry<String, List<SchemaPropertyDTO>> entry : schemas.entrySet()) {
            String modelName = entry.getKey();
            List<SchemaPropertyDTO> responseProperties = entry.getValue();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("modelName", modelName);

            List<Map<String, String>> properties = new ArrayList<>();
            List<Map<String, String>> otherTypes = new ArrayList<>();
            for (SchemaPropertyDTO schemaPropertyDTO : responseProperties) {
                Map<String, String> property = new HashMap<>();
                Map<String, String> otherType = new HashMap<>();

                property.put("name", schemaPropertyDTO.getName());
                if (schemaPropertyDTO.getTypeScriptType().equals("any")) {
                    String type = schemaPropertyDTO.getType();
                    property.put("default", "new "+type+"()");
                    otherType.put("name", type);
                    otherTypes.add(otherType);
                } else {
                    property.put("default", TypeScriptDefaultValue.getDefaultValueForType(schemaPropertyDTO.getTypeScriptType()));
                }
                properties.add(property);
            }
            dataModel.put("properties", properties);
            dataModel.put("otherTypes", otherTypes);

            Template template = cfg.getTemplate("Model.ftl");
            try (Writer fileWriter = new FileWriter(outputDir + "/" + modelName + ".js")) {
                template.process(dataModel, fileWriter);
            }
        }
    }
}
