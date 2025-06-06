package com.ravijar.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    public void createFile(File fileDir, String fileName, String content) throws IOException {
        File file = new File(fileDir, fileName);
        if (file.createNewFile()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            }
            logger.debug("{} created in {}.", fileName, fileDir.getName());

        } else {
            logger.warn("{} already exists in {}.", fileName, fileDir.getName());
        }
    }

    public void copyAllTemplates(String sourceRootPath, String destinationRootPath) {
        TemplatesConfigLoader templatesConfigLoader = new TemplatesConfigLoader();
        List<TemplatesConfigLoader.TemplateMapping> templateMappingList = templatesConfigLoader.getTemplateMappingList();
        for (TemplatesConfigLoader.TemplateMapping templateMappings : templateMappingList) {
            copyTemplates(templateMappings, sourceRootPath, destinationRootPath);
        }

    }

    public void copyTemplates(TemplatesConfigLoader.TemplateMapping mapping, String sourceRootPath, String destinationRootPath) {
        for (String template : mapping.getTemplates()) {
            String resourcePath = sourceRootPath + mapping.getSourceFolder() + template + mapping.getExtension();
            String destinationPath = destinationRootPath + mapping.getDestinationFolder() + template + mapping.getExtension();

            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
                if (inputStream == null) {
                    logger.error("Resource not found: " + resourcePath);
                    continue;
                }

                File destinationFile = new File(destinationPath);

                // Create parent directories for the destination if they don't exist
                destinationFile.getParentFile().mkdirs();

                // Copy the input stream to the destination file
                Files.copy(inputStream, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                logger.debug("Copied: " + resourcePath + " to " + destinationPath);
            } catch (IOException e) {
                logger.error("Error copying template from " + resourcePath + " to " + destinationPath, e);
            }
        }
    }

    public void copyFile(File sourceFile, File destFile) {
        if (!sourceFile.exists()) {
            logger.error("Source file {} does not exist.", sourceFile.getAbsolutePath());
            return;
        }
        if (sourceFile.isDirectory()) {
            logger.error("Source file {} is a directory, not a file.", sourceFile.getAbsolutePath());
            return;
        }

        try {
            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.debug("File {} copied to {}.", sourceFile.getAbsolutePath(), destFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public void createDirectoryIfNotExists(File directory) {
        if (directory.exists()) {
            if (directory.isDirectory()) {
                logger.debug("Directory {} already exists.", directory.getAbsolutePath());
            } else {
                logger.error("{} exists but is not a directory.", directory.getAbsolutePath());
            }
        } else {
            if (directory.mkdirs()) {
                logger.debug("Directory {} created successfully.", directory.getAbsolutePath());
            } else {
                logger.error("Failed to create directory {}.", directory.getAbsolutePath());
            }
        }
    }

    public void createSubDirectory(File baseDir, String directoryName) {
        File directory = new File(baseDir, directoryName);
        if (directory.mkdirs()) {
            logger.debug("{} directory created in {}.", directoryName, baseDir.getName());
        } else if (directory.exists()) {
            logger.warn("{} directory already exists in {}.", directoryName, baseDir.getName());
        } else {
            logger.error("Failed to create {} directory in {}.", directoryName, baseDir.getName());
        }
    }

    public void copyAllFilesFromDirectory(File inputDir, File outputDir) {
        if (!inputDir.isDirectory()) {
            logger.error("{} is not a directory.", inputDir.getAbsolutePath());
            return;
        }

        if (!outputDir.exists() && !outputDir.mkdirs()) {
            logger.error("Failed to create output directory {}.", outputDir.getAbsolutePath());
            return;
        }

        File[] files = inputDir.listFiles();
        if (files == null) {
            logger.error("Error reading files from directory {}.", inputDir.getAbsolutePath());
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                File destFile = new File(outputDir, file.getName());
                copyFile(file, destFile);
            }
        }
    }

    public void cleanupProjectDirectory(String projectPath) {
        logger.info("Cleaning up project directory...");
        
        File projectDir = new File(projectPath);
        if (projectDir.exists()) {
            try {
                if (!projectDir.canWrite()) {
                    logger.error("No write permissions for directory: {}", projectDir.getAbsolutePath());
                    return;
                }
                boolean success = deleteDirectory(projectDir);
                if (success) {
                    logger.info("Project directory cleaned up successfully.");
                } else {
                    logger.error("Failed to completely clean up project directory. Some files may remain.");
                }
            } catch (Exception e) {
                logger.error("Error during project cleanup: {}", e.getMessage());
            }
        } else {
            logger.warn("Project directory {} does not exist", projectPath);
        }
    }

    private boolean deleteDirectory(File directory) {
        boolean success = true;
        
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    if (!file.canWrite()) {
                        logger.error("No write permissions for: {}", file.getAbsolutePath());
                        success = false;
                        continue;
                    }
                    
                    if (file.isDirectory()) {
                        success = deleteDirectory(file) && success;
                    } else {
                        // Try multiple times in case file is locked
                        for (int i = 0; i < 3; i++) {
                            if (file.delete()) {
                                break;
                            }
                            if (i < 2) {
                                Thread.sleep(100); // Wait briefly before retry
                            } else {
                                logger.error("Failed to delete file after retries: {}", file.getAbsolutePath());
                                success = false;
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error deleting {}: {}", file.getAbsolutePath(), e.getMessage());
                    success = false;
                }
            }
        }
        
        try {
            // Try to delete the directory itself
            for (int i = 0; i < 3; i++) {
                if (directory.delete()) {
                    break;
                }
                if (i < 2) {
                    Thread.sleep(100); // Wait briefly before retry
                } else {
                    logger.error("Failed to delete directory after retries: {}", directory.getAbsolutePath());
                    success = false;
                }
            }
        } catch (Exception e) {
            logger.error("Error deleting directory {}: {}", directory.getAbsolutePath(), e.getMessage());
            success = false;
        }
        
        return success;
    }

}
