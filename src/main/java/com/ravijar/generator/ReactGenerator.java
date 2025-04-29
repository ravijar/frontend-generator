package com.ravijar.generator;

import com.ravijar.model.freemarker.*;
import com.ravijar.model.openapi.OpenAPISchemaProperty;
import com.ravijar.parser.OpenAPIParser;
import com.ravijar.model.xml.Page;
import com.ravijar.populator.PagePopulator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class ReactGenerator {
    private final Configuration cfg;
    private final OpenAPIParser openAPIParser;
    private final Map<String, List<OpenAPISchemaProperty>> schemas;
    private final CSSGenerator cssGenerator;
    private final JSGenerator jsGenerator;
    private final List<FreeMarkerPage> freeMarkerPages;

    public ReactGenerator(Configuration cfg, OpenAPIParser openAPIParser) {
        this.cfg = cfg;
        this.cssGenerator = new CSSGenerator(cfg);
        this.jsGenerator = new JSGenerator(cfg);
        this.openAPIParser = openAPIParser;
        this.schemas = openAPIParser.getSchemas();
        this.freeMarkerPages = new ArrayList<>();
    }

    public void generateAppPage(String outputDir) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("pages", freeMarkerPages);

        Template template = cfg.getTemplate("react/pages/App.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/App.jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void generateNavBar(String outputDir) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        List<FreeMarkerPage> navBarPages = new ArrayList<>();
        for (FreeMarkerPage page : freeMarkerPages) {
            if (page.isVisibleInNavBar()) {
                navBarPages.add(page);
            }
        }
        dataModel.put("pages", navBarPages);

        Template template = cfg.getTemplate("react/components/NavBar/Generate.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/NavBar.jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void generateForm(String outputDir, FreeMarkerComponent component) throws IOException, TemplateException {
        String formName = component.getId().substring(0, 1).toUpperCase() + component.getId().substring(1);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("component", component);

        Template template = cfg.getTemplate("react/components/Form/Generate.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + formName + ".jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void generatePage(String pageOutputDir, String componentOutputDir, String userStylesDir, Page page) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        FreeMarkerPage freeMarkerPage = new FreeMarkerPage();
        new PagePopulator(openAPIParser).populate(page, freeMarkerPage);

        for (FreeMarkerComponent component : freeMarkerPage.getComponents()) {
            if (component.getType().equals("Form")) {
                generateForm(componentOutputDir, component);
            }
        }

        dataModel.put("page", freeMarkerPage);
        freeMarkerPages.add(freeMarkerPage);

        Template template = cfg.getTemplate("react/pages/Page.ftl");
        try (Writer fileWriter = new FileWriter(pageOutputDir + "/" + page.getName() + ".jsx")) {
            template.process(dataModel, fileWriter);
        }

        this.cssGenerator.generatePageCSS(userStylesDir + "/pages", freeMarkerPage);
        this.jsGenerator.generatePageStyleJS(userStylesDir + "/custom_styles", freeMarkerPage);
    }
}
