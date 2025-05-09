package com.ravijar.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ravijar.model.xml.Page;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private static final Logger logger = LogManager.getLogger(XMLParser.class);

    private final String pagesFilePath;

    public XMLParser(String baseDir) {
        this.pagesFilePath = baseDir + "\\pages.xml";
    }

    public List<Page> getPages() {
        List<Page> pages = new ArrayList<>();
        try {
            XmlMapper xmlMapper = new XmlMapper();
            File pagesFile = new File(pagesFilePath);
            pages = xmlMapper.readValue(pagesFile, xmlMapper.getTypeFactory().constructCollectionType(List.class, Page.class));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return pages;
    }
}

