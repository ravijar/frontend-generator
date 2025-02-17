package com.ravijar.cli;

import com.ravijar.core.ProjectManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "validate", description = "Validate Pages.XML file.")

public class ValidateXMLCommand implements Runnable{
    private final ProjectManager projectManager;

    public ValidateXMLCommand(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Option(names = {"--xml"}, description = "Specify the xml path", required = true)
    private String xmlPath;

    @Override
    public void run() {
        projectManager.validateXML(xmlPath);
    }
}
