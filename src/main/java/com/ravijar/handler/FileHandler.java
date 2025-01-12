package com.ravijar.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
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
            logger.debug("{} created in {}." ,fileName, fileDir.getName());

        } else {
            logger.warn("{} already exists in {}." ,fileName, fileDir.getName());
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

//    public void createDirectory(File baseDir, String directoryName) {
//        File directory = new File(baseDir, directoryName);
//        if (directory.mkdirs()) {
//            logger.debug("{} directory created in {}.", directoryName, baseDir.getName());
//        } else if (directory.exists()) {
//            logger.warn("{} directory already exists in {}.", directoryName, baseDir.getName());
//        } else {
//            logger.error("Failed to create {} directory in {}.", directoryName, baseDir.getName());
//        }
//    }

    public void createDirectoryStructure(File rootDir, String[] subDirs) {
        // Create the root directory if it doesn't exist
        if (!rootDir.exists()) {
            if (rootDir.mkdirs()) {
                logger.debug("Created root directory: {}", rootDir.getAbsolutePath());
            } else {
                logger.error("Failed to create root directory: {}", rootDir.getAbsolutePath());
                return;
            }
        }

        // Create each subdirectory inside the root directory
        for (String subDir : subDirs) {
            File dir = new File(rootDir,subDir);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    logger.debug("Created subdirectory: {}", dir.getAbsolutePath());
                } else {
                    logger.error("Failed to create subdirectory: {}", dir.getAbsolutePath());
                }
            }
        }
    }

    public void createDirectoryIfNotExists(File directory) {
        if (directory.exists()) {
            if (directory.isDirectory()) {
                logger.info("Directory {} already exists.", directory.getAbsolutePath());
            } else {
                logger.error("{} exists but is not a directory.", directory.getAbsolutePath());
            }
        } else {
            if (directory.mkdirs()) {
                logger.info("Directory {} created successfully.", directory.getAbsolutePath());
            } else {
                logger.error("Failed to create directory {}.", directory.getAbsolutePath());
            }
        }
    }

    public void createSubDirectory(File baseDir, String directoryName) {
        File directory = new File(baseDir, directoryName);
        if (directory.mkdirs()) {
            logger.info("{} directory created in {}.", directoryName, baseDir.getName());
        } else if (directory.exists()) {
            logger.warn("{} directory already exists in {}.", directoryName, baseDir.getName());
        } else {
            logger.error("Failed to create {} directory in {}.", directoryName, baseDir.getName());
        }
    }

    public void copyResource(String resourcePath, File destFile) {
        try (InputStream in = getClass().getResourceAsStream(resourcePath);
             BufferedInputStream bufferedIn = new BufferedInputStream(in);
             FileOutputStream fileOut = new FileOutputStream(destFile);
             BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut)) {

            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedIn.read(buffer)) != -1) {
                bufferedOut.write(buffer, 0, bytesRead);
            }

            logger.debug("Resource copied successfully from {} to {}", resourcePath, destFile.getAbsolutePath());

        } catch (IOException e) {
            logger.error("Error copying resource: {}", resourcePath, e);
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

}
