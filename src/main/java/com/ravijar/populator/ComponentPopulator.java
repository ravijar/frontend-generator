package com.ravijar.populator;

import com.ravijar.helper.StringHelper;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerPage;
import com.ravijar.model.xml.component.Component;
import com.ravijar.parser.OpenAPIParser;

public abstract class ComponentPopulator {
    protected OpenAPIParser openAPIParser;
    protected FreeMarkerPage page;

    public ComponentPopulator(OpenAPIParser openAPIParser, FreeMarkerPage page) {
        this.openAPIParser = openAPIParser;
        this.page = page;
    }

    protected void populateComponent(Component source, FreeMarkerComponent target) {
        target.setType(source.getType());
        target.setId(source.getId());
        target.setStyleId(StringHelper.toKebabCase(source.getId()));
    }
}
