package com.ravijar.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocalStorage {
    @JacksonXmlProperty(isAttribute = true)
    private String action;

    @JacksonXmlProperty(localName = "assign")
    private Assign assign;
}
