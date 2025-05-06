package com.ravijar.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Property {
    @JacksonXmlProperty(isAttribute = true)
    private String key;

    @JacksonXmlProperty(isAttribute = true)
    private String value;
}
