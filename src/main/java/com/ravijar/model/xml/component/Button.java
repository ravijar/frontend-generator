package com.ravijar.model.xml.component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ravijar.model.xml.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Button extends Component{
    @JacksonXmlProperty(localName = "text")
    private Text text;

    @JacksonXmlProperty(localName = "route")
    private Route route;

    @JacksonXmlProperty(localName = "resource")
    private Resource resource;

    @JacksonXmlProperty(localName = "result")
    private Result result;

    @JacksonXmlProperty(localName = "localStorage")
    private LocalStorage localStorage;

    public Button() {
        this.setType("Button");
    }
}
