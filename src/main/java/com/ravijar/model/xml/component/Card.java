package com.ravijar.model.xml.component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Card extends Component {
    @JacksonXmlProperty(localName = "component")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Component> subComponents;

    public Card() {
        this.setType("Card");
    }
}