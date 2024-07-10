package com.ravijar.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    public void createFile(File fileDir, String fileName, String content) throws IOException {
        File file = new File(fileDir, fileName);
        if (file.createNewFile()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            }
            logger.info("{} created in {}." ,fileName, fileDir.getName());

        } else {
            logger.warn("{} already exists in {}." ,fileName, fileDir.getName());
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            logger.error("Source file {} does not exist.", sourceFile.getAbsolutePath());
            return;
        }
        if (sourceFile.isDirectory()) {
            logger.error("Source file {} is a directory, not a file.", sourceFile.getAbsolutePath());
            return;
        }

        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        logger.info("File {} copied to {}.", sourceFile.getAbsolutePath(), destFile.getAbsolutePath());
    }

    public void createDirectory(File baseDir, String directoryName) {
        File directory = new File(baseDir, directoryName);
        if (directory.mkdirs()) {
            logger.info("{} directory created in {}.", directoryName, baseDir.getName());
        } else if (directory.exists()) {
            logger.warn("{} directory already exists in {}.", directoryName, baseDir.getName());
        } else {
            logger.error("Failed to create {} directory in {}.", directoryName, baseDir.getName());
        }
    }
}
