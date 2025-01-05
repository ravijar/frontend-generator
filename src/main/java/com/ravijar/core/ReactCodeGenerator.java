package com.ravijar.core;

import com.ravijar.handler.OpenapiFileHandler;
import com.ravijar.model.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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
