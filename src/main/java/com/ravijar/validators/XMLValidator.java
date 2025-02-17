package com.ravijar.validators;

import com.ravijar.handler.TemplatesConfigLoader;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.io.FileUtils.getFile;

public class XMLValidator {
    private Validator initValidator() {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("pageConfig.xsd")) {
            if (inputStream == null) {
                throw new RuntimeException("Error:pageConfig.xsd not found in the classpath.");
            }
            Source schemaFile = new StreamSource(inputStream);
            Schema schema = factory.newSchema(schemaFile);
            return schema.newValidator();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error reading pageConfig.xsd file: ");
        }
    }

    public boolean isValid(String xmlPath) throws IOException, SAXException {
        Validator validator = initValidator();
        validator.validate(new StreamSource(getFile(xmlPath)));
        return true;
    }
}

