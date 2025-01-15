package com.ravijar.populator;

import com.ravijar.helper.PopulatorSwitch;
import com.ravijar.model.freemarker.FreeMarkerContainer;
import com.ravijar.model.xml.component.Container;
import com.ravijar.parser.OpenAPIParser;

public class ContainerPopulator extends ComponentPopulator{
    public ContainerPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(Container source, FreeMarkerContainer target) {
        populateComponent(source, target);
        target.setResource(openAPIParser.getResourceData(source.getResource()));
        target.setResultComponent(new PopulatorSwitch(openAPIParser).switchComponent(source.getResult().getComponent()));
    }
}
