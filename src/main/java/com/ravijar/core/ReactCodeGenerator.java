package com.ravijar.core;

import com.ravijar.handler.OpenapiFileHandler;
import com.ravijar.handler.PagesFileHandler;
import com.ravijar.model.Page;
import com.ravijar.model.ResponseProperty;
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
        List<ResponseProperty> responseProperties = openapiFileHandler.getResponseSchema(page.getResourceUrl(), PathItem.HttpMethod.GET, "200");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("pageName", page.getPageName());
        dataModel.put("endpointUrl", openapiFileHandler.getUrlEndpoint(page.getResourceUrl()));

        List<Map<String, String>> fields = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Map<String, String> field = new HashMap<>();
            field.put("name", parameter.getName());
            fields.add(field);
        }
        dataModel.put("fields", fields);

        List<Map<String, String>> data = new ArrayList<>();
        for (ResponseProperty responseProperty : responseProperties) {
            Map<String, String> datum = new HashMap<>();
            datum.put("property", responseProperty.getProperty());
            data.add(datum);
        }
        dataModel.put("data", data);

        Template template = cfg.getTemplate("Page.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + page.getPageName() + ".js")) {
            template.process(dataModel, fileWriter);
        }
    }

}
