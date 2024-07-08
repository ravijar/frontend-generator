package com.ravijar.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public void createFile(File fileDir, String fileName, String content) throws IOException {
        File file = new File(fileDir, fileName);
        if (file.createNewFile()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            }
            System.out.println(fileName + " created in " + fileDir.getName() + ".");
        } else {
            System.out.println(fileName + " already exists in " + fileDir.getName() + ".");
        }
    }

    public void createDirectory(File baseDir, String directoryName) {
        File directory = new File(baseDir, directoryName);
        if (directory.mkdir()) {
            System.out.println(directoryName + " directory created in " + baseDir.getName() + ".");
        } else {
            System.out.println(directoryName + " directory already exists in " + baseDir.getName() + ".");
        }
    }
}
