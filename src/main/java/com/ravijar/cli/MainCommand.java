package com.ravijar.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "fegen", version = "FeGenerator 1.0",mixinStandardHelpOptions = true)
public class MainCommand implements Runnable{
    @Override
    public void run() {
        System.out.println("Welcome to FeGenerator 1.0!");
        System.out.println("Frontend Generator CLI Tool");
        System.out.println("For help and available commands, use: java -jar fegen.jar --help");
    }
}