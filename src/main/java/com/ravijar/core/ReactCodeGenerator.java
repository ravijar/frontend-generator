package com.ravijar.core;

import com.ravijar.handler.OpenapiFileHandler;
import com.ravijar.model.Page;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactCodeGenerator {
    private final Configuration cfg;

    public ReactCodeGenerator(Configuration cfg) {
        this.cfg = cfg;
    }

    public void updateAppPage(String outputDir, String pageName) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("pageName", pageName);

        Template template = cfg.getTemplate("App.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/App.js")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void createPage(String outputDir, Page page) throws IOException, TemplateException {
        OpenapiFileHandler openapiFileHandler = new OpenapiFileHandler();
        List<Parameter> parameters = openapiFileHandler.getParameters(page.getResourceUrl(), page.getResourceMethod());

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("pageName", page.getPageName());
        dataModel.put("fieldName", parameters.get(0).getName());

        Template template = cfg.getTemplate("Page.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + page.getPageName() + ".js")) {
            template.process(dataModel, fileWriter);
        }
    }
}
