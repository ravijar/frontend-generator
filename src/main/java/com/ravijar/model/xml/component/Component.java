package com.ravijar.model.xml.component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HeroSection.class, name = "Herosection"),
        @JsonSubTypes.Type(value = SearchBar.class, name = "Searchbar"),
        @JsonSubTypes.Type(value = Button.class, name = "Button"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Component {
    @JacksonXmlProperty(isAttribute = true)
    private String type;
}
