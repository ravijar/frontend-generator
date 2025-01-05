package com.ravijar.model.xml.component;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HeroSection.class, name = "HeroSection"),
        @JsonSubTypes.Type(value = SearchBar.class, name = "SearchBar"),
        @JsonSubTypes.Type(value = Button.class, name = "Button"),
        @JsonSubTypes.Type(value = Form.class, name = "Form"),
        @JsonSubTypes.Type(value = CardSection.class, name = "CardSection"),
        @JsonSubTypes.Type(value = Container.class, name = "Container"),
})

@Getter
@Setter
@ToString
public abstract class Component {
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String id;
}
