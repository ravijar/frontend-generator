package com.ravijar.model.xml.component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ravijar.model.xml.Route;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Button extends Component{
    @JacksonXmlProperty(isAttribute = true)
    private String text;

    @JacksonXmlProperty(localName = "resource")
    private Route route;
}
