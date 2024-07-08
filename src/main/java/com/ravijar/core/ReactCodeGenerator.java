package com.ravijar.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ReactCodeGenerator {
    private final Configuration cfg;

    public ReactCodeGenerator(Configuration cfg) {
        this.cfg = cfg;
    }

    public void generateReactComponent(String outputDir) throws IOException, TemplateException {

        String componentName = "Test";

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("componentName", componentName);

        Template template = cfg.getTemplate("ComponentTemplate.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + componentName + ".js")) {
            template.process(dataModel, fileWriter);
        }
    }
}
