package com.ravijar.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "init", description = "Initializes a new project.")
public class InitCommand implements Runnable {

    @Option(names = {"-t", "--template"}, description = "Specify the project template", required = false)
    private String template;

    @Override
    public void run() {
        System.out.println("Initializing a new project with template: " + template);
        // Logic for initializing the project
    }
}
