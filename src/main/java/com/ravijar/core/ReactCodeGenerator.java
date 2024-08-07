package com.ravijar.core;

import com.ravijar.handler.OpenapiFileHandler;
import com.ravijar.model.Page;
import com.ravijar.model.SchemaProperty;
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
    private final Map<String, List<SchemaProperty>> schemas;

    public ReactCodeGenerator(Configuration cfg) {
        this.cfg = cfg;
        this.openapiFileHandler = new OpenapiFileHandler();
        this.schemas = openapiFileHandler.getSchemas();
    }

    public void updateAppPage(String outputDir, List<Page> pageList) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        List<Map<String, String>> pages = new ArrayList<>();
        for (Page page : pageList) {
            Map<String, String> pageData = new HashMap<>();
            pageData.put("name", page.getPageName());
            pages.add(pageData);
        }
        dataModel.put("pages", pages);

        Template template = cfg.getTemplate("App.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/App.js")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void createPage(String outputDir, Page page) throws IOException, TemplateException {
        List<Parameter> parameters = openapiFileHandler.getParameters(page.getResourceUrl(), page.getResourceMethod());
        String responseSchemaName = openapiFileHandler.getResponseSchemaName(page.getResourceUrl(), page.getResourceMethod(),"200");
        String responseSchemaType = openapiFileHandler.getResponseSchemaType(page.getResourceUrl(), page.getResourceMethod(),"200");
        String requestSchema = openapiFileHandler.getRequestSchema(page.getResourceUrl(), page.getResourceMethod());
        List<String> nextPageList = openapiFileHandler.getNextPages(page.getResourceUrl(), page.getResourceMethod(), "200");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("pageName", page.getPageName());
        dataModel.put("apiMethod", openapiFileHandler.getOperationId(page.getResourceUrl(), page.getResourceMethod()));
        dataModel.put("requestSchema", requestSchema);
        dataModel.put("httpMethod", page.getResourceMethod().toString());
        dataModel.put("customStyled", page.isCustomStyled());

        Set<String> responseCodes = openapiFileHandler.getResponseCodes(page.getResourceUrl(), page.getResourceMethod());
        List<Map<String, String>> displayNames = new ArrayList<>();
        for (String code : responseCodes) {
            List<SchemaProperty> responseSchema = schemas.get(openapiFileHandler.getResponseSchemaName(page.getResourceUrl(), page.getResourceMethod(), code));
            for (SchemaProperty schemaProperty : responseSchema) {
                Map<String, String> displayName = new HashMap<>();
                if (schemaProperty.getDisplayName() != null) {
                    displayName.put(schemaProperty.getName(), schemaProperty.getDisplayName());
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
            for (SchemaProperty schemaProperty : schemas.get(requestSchema)) {
                Map<String, String> field = new HashMap<>();
                field.put("name", schemaProperty.getName());
                field.put("displayName", schemaProperty.getDisplayName());
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
        try (Writer fileWriter = new FileWriter(outputDir + "/" + page.getPageName() + ".js")) {
            template.process(dataModel, fileWriter);
        }
    }

    @Deprecated
    public void generateModels(String outputDir) throws IOException, TemplateException {
        for (Map.Entry<String, List<SchemaProperty>> entry : schemas.entrySet()) {
            String modelName = entry.getKey();
            List<SchemaProperty> responseProperties = entry.getValue();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("modelName", modelName);

            List<Map<String, String>> properties = new ArrayList<>();
            List<Map<String, String>> otherTypes = new ArrayList<>();
            for (SchemaProperty schemaProperty : responseProperties) {
                Map<String, String> property = new HashMap<>();
                Map<String, String> otherType = new HashMap<>();

                property.put("name", schemaProperty.getName());
                if (schemaProperty.getTypeScriptType().equals("any")) {
                    String type = schemaProperty.getType();
                    property.put("default", "new "+type+"()");
                    otherType.put("name", type);
                    otherTypes.add(otherType);
                } else {
                    property.put("default", TypeScriptDefaultValue.getDefaultValueForType(schemaProperty.getTypeScriptType()));
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
