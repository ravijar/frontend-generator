package com.ravijar.populator;

import com.ravijar.helper.StringConverter;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.xml.component.Component;
import com.ravijar.parser.OpenAPIParser;

public abstract class ComponentPopulator {
    protected OpenAPIParser openAPIParser;

    public ComponentPopulator(OpenAPIParser openAPIParser) {
        this.openAPIParser = openAPIParser;
    }

    protected void populateComponent(Component source, FreeMarkerComponent target) {
        target.setType(source.getType());
        target.setId(source.getId());
        target.setStyleId(StringConverter.toKebabCase(source.getId()));
    }
}
