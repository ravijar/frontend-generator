package com.ravijar.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TemplatesConfigLoader {
    private static final String TEMPLATE_FILE_PATH = "src/main/resources/templates.json";
    @Getter
    private List<TemplateMapping> templateMappingList;
    private ObjectMapper objectMapper;

    public TemplatesConfigLoader() {
        objectMapper = new ObjectMapper();
        loadTemplatesConfig();
    }

    private void loadTemplatesConfig() {
        try {
            TemplatesConfig templatesConfig = objectMapper.readValue(new File(TEMPLATE_FILE_PATH), TemplatesConfig.class);
            templateMappingList = templatesConfig.getTemplateMappings();
        } catch (IOException e) {
            throw new RuntimeException("Error reading or parsing the templates file", e);
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
