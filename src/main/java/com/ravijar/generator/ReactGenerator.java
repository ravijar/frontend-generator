package com.ravijar.generator;

import com.ravijar.helper.StringHelper;
import com.ravijar.model.freemarker.*;
import com.ravijar.model.openapi.OpenAPIParameter;
import com.ravijar.model.openapi.OpenAPISchemaProperty;
import com.ravijar.parser.OpenAPIParser;
import com.ravijar.model.*;
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

    public ReactGenerator(Configuration cfg, OpenAPIParser openAPIParser) {
        this.cfg = cfg;
        this.cssGenerator = new CSSGenerator(cfg);
        this.jsGenerator = new JSGenerator(cfg);
        this.openAPIParser = openAPIParser;
        this.schemas = openAPIParser.getSchemas();
    }

    public void generateAppPage(String outputDir, List<Page> pages) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        List<FreeMarkerPage> freeMarkerPages = new ArrayList<>();
        for (Page page : pages) {
            freeMarkerPages.add(new FreeMarkerPage(
                    page.getName(),
                    page.getRoute(),
                    StringHelper.toColonRoute(page.getRoute()),
                    StringHelper.extractUrlParameter(page.getRoute()),
                    null
            ));
        }
        dataModel.put("pages", freeMarkerPages);

        Template template = cfg.getTemplate("react/pages/App.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/App.jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    public void generateNavBar(String outputDir, List<Page> pages) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();

        List<FreeMarkerPage> freeMarkerPages = new ArrayList<>();
        for (Page page : pages) {
            if (page.isNavbar()) {
                freeMarkerPages.add(new FreeMarkerPage(
                        page.getName(),
                        page.getRoute(),
                        StringHelper.toColonRoute(page.getRoute()),
                        StringHelper.extractUrlParameter(page.getRoute()),
                        null
                ));
            }
        }
        dataModel.put("pages", freeMarkerPages);

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

        Template template = cfg.getTemplate("react/pages/Page.ftl");
        try (Writer fileWriter = new FileWriter(pageOutputDir + "/" + page.getName() + ".jsx")) {
            template.process(dataModel, fileWriter);
        }

        this.cssGenerator.generatePageCSS(userStylesDir + "/pages", freeMarkerPage);
        this.jsGenerator.generatePageStyleJS(userStylesDir + "/custom_styles", freeMarkerPage);
    }

    @Deprecated
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
        try (Writer fileWriter = new FileWriter(outputDir + "/App.jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    @Deprecated
    public void createPage(String outputDir, PageDTO pageDTO) throws IOException, TemplateException {
        List<OpenAPIParameter> parameters = openAPIParser.getParameters(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        String requestSchema = openAPIParser.getRequestSchema(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        openAPIParser.getPageExtensions(pageDTO);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("apiMethod", openAPIParser.getOperationId(pageDTO.getResourceUrl(), pageDTO.getResourceMethod()));
        dataModel.put("requestSchema", requestSchema);
        dataModel.put("pageDTO",pageDTO);

        Set<String> responseCodes = openAPIParser.getResponseCodes(pageDTO.getResourceUrl(), pageDTO.getResourceMethod());
        List<Map<String, String>> displayNames = new ArrayList<>();
        List<ResponseDTO> responses= new ArrayList<>();
        for (String code : responseCodes) {
            String responseSchemaName = openAPIParser.getResponseSchemaName(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code);
            String responseSchemaType = openAPIParser.getResponseSchemaType(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code);
            String responseDescription = openAPIParser.getResponseDescription(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code);
            List<String> nextPageList = openAPIParser.getNextPages(pageDTO.getResourceUrl(), pageDTO.getResourceMethod(), code);

            List<OpenAPISchemaProperty> result = schemas.get(responseSchemaName);
            if (result != null) {
                for (OpenAPISchemaProperty openAPISchemaProperty : result) {
                    Map<String, String> displayName = new HashMap<>();
                    if (openAPISchemaProperty.getDisplayName() != null) {
                        displayName.put(openAPISchemaProperty.getName(), openAPISchemaProperty.getDisplayName());
                        if (!displayNames.contains(displayName)) {
                            displayNames.add(displayName);
                        }
                    }
                }
            }

            ResponseDTO responseDTO = new ResponseDTO(code, responseDescription, responseSchemaName, responseSchemaType, nextPageList);
            responses.add(responseDTO);
        }
        dataModel.put("responses", responses);

        List<Map<String, String>> fields = new ArrayList<>();
        List<Map<String, String>> requestParams = new ArrayList<>();
        for (OpenAPIParameter parameter : parameters) {
            Map<String, String> field = new HashMap<>();
            field.put("name", parameter.getName());
            if (!fields.contains(field)) {
                fields.add(field);
            }

            if (parameter.getDisplayName() != null) {
                Map<String, String> displayName = new HashMap<>();
                displayName.put(parameter.getName(), parameter.getDisplayName());
                if (!displayNames.contains(displayName)) {
                    displayNames.add(displayName);
                }
            }
        }

        if (requestSchema != null) {
            for (OpenAPISchemaProperty openAPISchemaProperty : schemas.get(requestSchema)) {
                Map<String, String> field = new HashMap<>();
                field.put("name", openAPISchemaProperty.getName());
                if (!fields.contains(field)) {
                    fields.add(field);
                }

                if (openAPISchemaProperty.getDisplayName() != null) {
                    Map<String, String> displayName = new HashMap<>();
                    displayName.put(openAPISchemaProperty.getName(), openAPISchemaProperty.getDisplayName());
                    if (!displayNames.contains(displayName)) {
                        displayNames.add(displayName);
                    }
                }

                requestParams.add(field);
            }
        }
        dataModel.put("fields", fields);
        dataModel.put("requestParams", requestParams);
        dataModel.put("displayNames", displayNames);

        Template template = cfg.getTemplate("Page.ftl");
        try (Writer fileWriter = new FileWriter(outputDir + "/" + pageDTO.getPageName() + ".jsx")) {
            template.process(dataModel, fileWriter);
        }
    }

    @Deprecated
    public void generateModels(String outputDir) throws IOException, TemplateException {
        for (Map.Entry<String, List<OpenAPISchemaProperty>> entry : schemas.entrySet()) {
            String modelName = entry.getKey();
            List<OpenAPISchemaProperty> responseProperties = entry.getValue();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("modelName", modelName);

            List<Map<String, String>> properties = new ArrayList<>();
            List<Map<String, String>> otherTypes = new ArrayList<>();
            for (OpenAPISchemaProperty openAPISchemaProperty : responseProperties) {
                Map<String, String> property = new HashMap<>();
                Map<String, String> otherType = new HashMap<>();

                property.put("name", openAPISchemaProperty.getName());
                if (openAPISchemaProperty.getTypeScriptType().equals("any")) {
                    String type = openAPISchemaProperty.getType();
                    property.put("default", "new "+type+"()");
                    otherType.put("name", type);
                    otherTypes.add(otherType);
                } else {
                    property.put("default", TypeScriptDefaultValue.getDefaultValueForType(openAPISchemaProperty.getTypeScriptType()));
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
