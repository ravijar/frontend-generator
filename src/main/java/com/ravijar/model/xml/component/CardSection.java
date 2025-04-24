package com.ravijar.model.xml.component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ravijar.model.xml.Assign;
import com.ravijar.model.xml.Route;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardSection extends Component {
    @JacksonXmlProperty(localName = "assign")
    private Assign assign;

    @JacksonXmlProperty(localName = "route")
    private Route route;

    public CardSection() {
        this.setType("CardSection");
    }
}
