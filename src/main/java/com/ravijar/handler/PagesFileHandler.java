package com.ravijar.handler;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ravijar.model.PageDTO;
import com.ravijar.model.xml.Page;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PagesFileHandler {
    private static final Logger logger = LogManager.getLogger(PagesFileHandler.class);

    private final String pagesFilePath;

    public PagesFileHandler(String baseDir) {
        this.pagesFilePath = baseDir + "\\pages.xml";
    }

    private HttpMethod getHttpMethod(String method) {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.name().equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        return null;
    }

    public List<PageDTO> getPages() {
        List<PageDTO> pageDTOs = new ArrayList<>();
        try {
            File pagesFile = new File(pagesFilePath);
            if (!pagesFile.exists() || !pagesFile.canRead()) {
                logger.error("File not found or not readable: {}", pagesFile.getAbsolutePath());
                return pageDTOs;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(pagesFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("page");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String resourceUrl = eElement.getAttribute("resource-url");
                    String pageName = eElement.getTextContent();
                    HttpMethod resourceMethod = getHttpMethod(eElement.getAttribute("resource-method"));
                    if (resourceMethod == null) {
                        logger.error("Invalid HTTP method '{}' in page '{}'.", eElement.getAttribute("resource-method"), pageName);
                        continue;
                    }
                    pageDTOs.add(new PageDTO(pageName,resourceUrl,resourceMethod,false, ""));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return pageDTOs;
    }

    public List<Page> getPagesNew() {
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

