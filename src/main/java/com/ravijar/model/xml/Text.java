package com.ravijar.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Text {
    @JacksonXmlText
    private String body;
}
