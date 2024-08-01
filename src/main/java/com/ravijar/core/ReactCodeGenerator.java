package com.ravijar.core;

import com.ravijar.handler.OpenapiFileHandler;
import com.ravijar.handler.PagesFileHandler;
import com.ravijar.model.Page;
import com.ravijar.model.ResponseProperty;
import com.ravijar.model.TypeScriptDefaultValue;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactCodeGenerator {
    private final Configuration cfg;

    public ReactCodeGenerator(Configuration cfg) {
        this.cfg = cfg;
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
        OpenapiFileHandler openapiFileHandler = new OpenapiFileHandler();
        List<Parameter> parameters = openapiFileHandler.getParameters(page.getResourceUrl(), page.getResourceMethod());
        String responseSchema = openapiFileHandler.getResponseSchema(page.getResourceUrl(), page.getResourceMethod(),"200");
        List<String> nextPageList = openapiFileHandler.getNextPages(page.getResourceUrl(), page.getResourceMethod(), "200");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("pageName", page.getPageName());
        dataModel.put("apiMethod", openapiFileHandler.getOperationId(page.getResourceUrl(), page.getResourceMethod()));
        dataModel.put("responseSchema", responseSchema);

        List<Map<String, String>> fields = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Map<String, String> field = new HashMap<>();
            field.put("name", parameter.getName());
            fields.add(field);
        }
        dataModel.put("fields", fields);

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

    public void generateModels(String outputDir, Map<String, List<ResponseProperty>> schemas) throws IOException, TemplateException {
        for (Map.Entry<String, List<ResponseProperty>> entry : schemas.entrySet()) {
            String modelName = entry.getKey();
            List<ResponseProperty> responseProperties = entry.getValue();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("modelName", modelName);

            List<Map<String, String>> properties = new ArrayList<>();
            List<Map<String, String>> otherTypes = new ArrayList<>();
            for (ResponseProperty responseProperty : responseProperties) {
                Map<String, String> property = new HashMap<>();
                Map<String, String> otherType = new HashMap<>();

                property.put("name", responseProperty.getProperty());
                if (responseProperty.getTypeScriptType().equals("any")) {
                    String type = responseProperty.getType();
                    property.put("default", "new "+type+"()");
                    otherType.put("name", type);
                    otherTypes.add(otherType);
                } else {
                    property.put("default", TypeScriptDefaultValue.getDefaultValueForType(responseProperty.getTypeScriptType()));
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
