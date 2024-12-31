package com.ravijar.model.xml.component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ravijar.model.xml.Content;
import com.ravijar.model.xml.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HeroSection extends Component{
    @JacksonXmlProperty(localName = "content")
    private Content content;

    @JacksonXmlProperty(localName = "image")
    private Image image;
}
