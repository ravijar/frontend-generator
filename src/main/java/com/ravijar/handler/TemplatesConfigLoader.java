package com.ravijar.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TemplatesConfigLoader {
    private static final String TEMPLATE_FILE_PATH = "../resources/templates.json";
    @Getter
    private List<TemplatesConfig> templatesConfigList;
    private ObjectMapper objectMapper;

    public TemplatesConfigLoader() {
        objectMapper = new ObjectMapper();
        loadTemplatesConfig();
    }

    private void loadTemplatesConfig() {
        try {
            templatesConfigList = objectMapper.readValue(
                    new File(TEMPLATE_FILE_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, TemplatesConfig.class)
            );
        } catch (IOException e) {
            throw new RuntimeException("Error reading or parsing the templates file", e);
        }
    }

    public static class TemplatesConfig {
        public String name;
        public String sourceFolder;
        public String destinationFolder;
        public String extension;
        public List<String> templates;
    }


}
