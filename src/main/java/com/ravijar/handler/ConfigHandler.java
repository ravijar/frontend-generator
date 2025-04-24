package com.ravijar.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConfigHandler {
    private static final Logger logger = LogManager.getLogger(ConfigHandler.class);
    private final String filePath;

    public ConfigHandler(String filePath) {
        this.filePath = filePath;
    }

    public boolean isPropertiesFileAvailable() {
        File configFile = new File(filePath);
        return configFile.exists();
    }

    public void createPropertiesFile(String projectName) {
        File configFile = new File(filePath);

        if (configFile.exists()) {
            logger.warn("Configuration file '{}' already exists.", filePath);
            return;
        }

        Properties properties = new Properties();
        properties.setProperty("projectName", projectName);

        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            properties.store(fos, "Project Configuration");
            logger.debug("Configuration file '{}' created successfully.", filePath);
        } catch (IOException e) {
            logger.error("Failed to create configuration file '{}': {}", filePath, e.getMessage());
        }
    }

    public void addOrUpdateProperty(String key, String value) {
        File configFile = new File(filePath);
        Properties properties = new Properties();

        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
            } catch (IOException e) {
                logger.error("Failed to load existing properties from config file: {}", e.getMessage());
            }
        }

        properties.setProperty(key, value);

        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            properties.store(fos, "Project Configuration");
            logger.debug("Property '{}' updated to '{}' in config.properties.", key, value);
        } catch (IOException e) {
            logger.error("Failed to save properties to config file: {}", e.getMessage());
        }
    }

    public String readProperty(String key) {
        File configFile = new File(filePath);

        if (!configFile.exists()) {
            logger.warn("Configuration file '{}' not found. Cannot retrieve property '{}'.", filePath, key);
            return null;
        }

        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
            String value = properties.getProperty(key);
            if (value == null) {
                logger.warn("Property '{}' not found in config.properties.", key);
                return null;
            }
            logger.debug("Property '{}' loaded with value '{}'.", key, value);
            return value;
        } catch (IOException e) {
            logger.error("Failed to read property '{}' from config file: {}", key, e.getMessage());
            return null;
        }
    }
}

