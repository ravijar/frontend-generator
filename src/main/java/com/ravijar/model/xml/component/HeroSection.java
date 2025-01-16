package com.ravijar.model.xml.component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ravijar.model.xml.Text;
import com.ravijar.model.xml.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class HeroSection extends Component{
    @JacksonXmlProperty(localName = "text")
    private Text text;

    @JacksonXmlProperty(localName = "image")
    private Image image;

    @JacksonXmlProperty(localName = "component")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Component> subComponents;

    public HeroSection() {
        this.setType("HeroSection");
    }
}
