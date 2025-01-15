package com.ravijar.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Resource {
    @JacksonXmlText
    private String url;

    @JacksonXmlProperty(isAttribute = true)
    private String method;
}
