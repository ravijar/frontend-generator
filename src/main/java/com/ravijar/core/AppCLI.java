package com.ravijar.core;

import com.ravijar.cli.*;
import com.ravijar.core.ProjectManager;
import picocli.CommandLine;

public class AppCLI {
    public static void main(String[] args) {

        CommandLine commandLine = new CommandLine(new MainCommand());
        ProjectManager projectManager=new ProjectManager();

        commandLine.addSubcommand("init", new InitCommand(projectManager));
        commandLine.addSubcommand("generate", new GenerateCommand(projectManager));
        commandLine.addSubcommand("build", new BuildCommand(projectManager));
        commandLine.addSubcommand("run",new RunCommand(projectManager));
        commandLine.addSubcommand("test",new TestCommand(projectManager));

        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
