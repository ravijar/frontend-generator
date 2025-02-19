package com.ravijar.model.xml.component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ravijar.model.xml.Route;
import com.ravijar.model.xml.Save;
import com.ravijar.model.xml.Text;
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

    @JacksonXmlProperty(localName = "save")
    private Save save;

    public Button() {
        this.setType("Button");
    }
}
