package com.ravijar.core;

import com.ravijar.model.freemarker.FreeMarkerPage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CSSGenerator {
    private final Configuration cfg;

    public CSSGenerator(Configuration cfg) {
        this.cfg = cfg;
    }

    public void generatePageCSS(String outputDir, FreeMarkerPage page) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("data", page);

        Template template = cfg.getTemplate("css/PageCSS.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + page.getName() + ".css")) {
            template.process(dataModel, fileWriter);
        }
    }
}
