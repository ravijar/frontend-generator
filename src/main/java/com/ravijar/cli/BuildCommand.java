package com.ravijar.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "build", description = "Builds the project.")
public class BuildCommand implements Runnable {

    @Option(names = {"-d", "--directory"}, description = "The directory to build the project", required = false)
    private String directory;

    @Override
    public void run() {
        System.out.println("Building project in directory: " + directory);
        // Logic for building the project
    }
}
