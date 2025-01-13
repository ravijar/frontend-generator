package com.ravijar.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TemplatesConfigLoader {
    private static final String TEMPLATE_FILE_NAME = "templates.json";
    @Getter
    private List<TemplateMapping> templateMappingList;
    private ObjectMapper objectMapper;

    public TemplatesConfigLoader() {
        objectMapper = new ObjectMapper();
        loadTemplatesConfig();
    }

    private void loadTemplatesConfig() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TEMPLATE_FILE_NAME)) {
            if (inputStream == null) {
                throw new RuntimeException("Error: " + TEMPLATE_FILE_NAME + " not found in the classpath.");
            }
            TemplatesConfig templatesConfig = objectMapper.readValue(inputStream, TemplatesConfig.class);
            templateMappingList = templatesConfig.getTemplateMappings();
        } catch (Exception e) {
            throw new RuntimeException("Error reading or parsing the templates file: " + TEMPLATE_FILE_NAME, e);
        }
    }

    @Setter
    @Getter
    @ToString
    public static class TemplatesConfig {
        private List<TemplateMapping> templateMappings;
    }

    @Getter
    @Setter
    @ToString
    public static class TemplateMapping {
        private String name;
        private String sourceFolder;
        private String destinationFolder;
        private String extension;
        private List<String> templates;
    }

}
