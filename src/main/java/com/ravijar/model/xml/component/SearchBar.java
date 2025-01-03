package com.ravijar.model.xml.component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ravijar.model.xml.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchBar extends Component{
    @JacksonXmlProperty(localName = "resource")
    private Resource resource;

    public SearchBar() {
        this.setType("SearchBar");
    }
}
