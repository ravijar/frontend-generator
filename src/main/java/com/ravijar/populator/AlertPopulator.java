package com.ravijar.populator;

import com.ravijar.model.freemarker.FreeMarkerAlert;
import com.ravijar.model.xml.component.Alert;
import com.ravijar.parser.OpenAPIParser;

public class AlertPopulator extends ComponentPopulator{
    public AlertPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(Alert source, FreeMarkerAlert target) {
        populateComponent(source, target);
    }
}
