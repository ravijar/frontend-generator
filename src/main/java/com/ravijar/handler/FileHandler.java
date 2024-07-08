package com.ravijar.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public void createDirectory(File baseDir, String directoryName) {
        File directory = new File(baseDir, directoryName);
        if (directory.mkdir()) {
            logger.info("{} directory created in {}.", directoryName, baseDir.getName());
        } else {
            logger.warn("{} directory already exists in {}.", directoryName, baseDir.getName());
        }
    }
}
