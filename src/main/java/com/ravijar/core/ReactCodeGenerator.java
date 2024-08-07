package com.ravijar.core;

import com.ravijar.handler.OpenapiFileHandler;
import com.ravijar.model.PageDTO;
import com.ravijar.model.SchemaPropertyDTO;
import com.ravijar.model.TypeScriptDefaultValue;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.models.parameters.Parameter;

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
        try (Writer fileWriter = new FileWriter(outputDir + "/App.js")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void createPage(String outputDir, PageDTO pageDTO) throws IOException, TemplateException {
        List<Parameter> parameters = openapiFileHandler.getParameters(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        String responseSchemaName = openapiFileHandler.getResponseSchemaName(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(),"200");
        String responseSchemaType = openapiFileHandler.getResponseSchemaType(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(),"200");
        String requestSchema = openapiFileHandler.getRequestSchema(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        List<String> nextPageList = openapiFileHandler.getNextPages(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), "200");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("pageName", pageDTO.getPageName());
        dataModel.put("apiMethod", openapiFileHandler.getOperationId(pageDTO.getResourceUrl(), pageDTO.getResourceMethod()));
        dataModel.put("requestSchema", requestSchema);
        dataModel.put("httpMethod", pageDTO.getResourceMethod().toString());
        dataModel.put("customStyled", pageDTO.isCustomStyled());

        Set<String> responseCodes = openapiFileHandler.getResponseCodes(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        List<Map<String, String>> displayNames = new ArrayList<>();
        for (String code : responseCodes) {
            List<SchemaPropertyDTO> responseSchema = schemas.get(openapiFileHandler.getResponseSchemaName(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code));
            for (SchemaPropertyDTO schemaPropertyDTO : responseSchema) {
                Map<String, String> displayName = new HashMap<>();
                if (schemaPropertyDTO.getDisplayName() != null) {
                    displayName.put(schemaPropertyDTO.getName(), schemaPropertyDTO.getDisplayName());
                    if (!displayNames.contains(displayName)) {
                        displayNames.add(displayName);
                    }
                }
            }
        }
        dataModel.put("displayNames", displayNames);

        Map<String, String> responseSchema = new HashMap<>();
        responseSchema.put("name", responseSchemaName);
        responseSchema.put("type", responseSchemaType);
        dataModel.put("responseSchema", responseSchema);

        List<Map<String, String>> fields = new ArrayList<>();
        List<Map<String, String>> requestParams = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Map<String, String> field = new HashMap<>();
            field.put("name", parameter.getName());
            field.put("displayName", openapiFileHandler.getExtentionString(parameter.getExtensions(),"x-displayName") );
            if (!fields.contains(field)) {
                fields.add(field);
            }

        }
        if (requestSchema != null) {
            for (SchemaPropertyDTO schemaPropertyDTO : schemas.get(requestSchema)) {
                Map<String, String> field = new HashMap<>();
                field.put("name", schemaPropertyDTO.getName());
                field.put("displayName", schemaPropertyDTO.getDisplayName());
                if (!fields.contains(field)) {
                    fields.add(field);
                }
                requestParams.add(field);
            }
        }
        dataModel.put("fields", fields);
        dataModel.put("requestParams", requestParams);

        List<Map<String,String>> nextPages = new ArrayList<>();
        for (String nextPage : nextPageList) {
            Map<String, String> nextPageData = new HashMap<>();
            nextPageData.put("name", nextPage);
            nextPages.add(nextPageData);
        }
        dataModel.put("nextPages", nextPages);

        Template template = cfg.getTemplate("Page.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + pageDTO.getPageName() + ".js")) {
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
