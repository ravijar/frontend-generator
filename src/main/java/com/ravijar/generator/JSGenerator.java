package com.ravijar.generator;

import com.ravijar.model.freemarker.FreeMarkerPage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

public class JSGenerator {
    private Configuration cfg;

    public JSGenerator(Configuration cfg) {
        this.cfg = cfg;
    }

    public void generatePageStyleJS(String outputDir, FreeMarkerPage page) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("data", page);

        Template template = cfg.getTemplate("js/styles/PageStyleJS.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + page.getName() + ".js")) {
            template.process(dataModel, fileWriter);
        }
    }
}
