package com.ravijar.populator;

import com.ravijar.helper.PopulatorSwitch;
import com.ravijar.model.freemarker.*;
import com.ravijar.model.xml.Page;
import com.ravijar.model.xml.component.*;
import com.ravijar.parser.OpenAPIParser;

import java.util.ArrayList;
import java.util.List;

public class PagePopulator {
    private OpenAPIParser openAPIParser;

    public PagePopulator(OpenAPIParser openAPIParser) {
        this.openAPIParser = openAPIParser;
    }

    public void populate(Page source, FreeMarkerPage target) {
        target.setName(source.getName());
        target.setRoute(source.getRoute());

        List<FreeMarkerComponent> freeMarkerComponents = new ArrayList<>();

        for(Component component : source.getComponents()) {
            freeMarkerComponents.add(new PopulatorSwitch(openAPIParser).switchComponent(component));
        }
        target.setComponents(freeMarkerComponents);
    }
}
