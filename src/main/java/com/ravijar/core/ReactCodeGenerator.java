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

    public void updateAppPage(String outputDir, String pageName) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("pageName", pageName);

        Template template = cfg.getTemplate("App.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/App.js")) {
            template.process(dataModel, fileWriter);
        }
    }
}
