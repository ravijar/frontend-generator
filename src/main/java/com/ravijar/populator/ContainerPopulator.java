package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerContainer;
import com.ravijar.model.xml.component.Container;
import com.ravijar.parser.OpenAPIParser;

public class ContainerPopulator extends ComponentPopulator{
    public ContainerPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(Container source, FreeMarkerContainer target) {
        populateComponent(source, target);

        if(source.getResource() != null) {
            target.setFetch("resource");
            target.setResource(openAPIParser.getResourceData(source.getResource()));
        }

        if(source.getLoad() != null) {
            target.setFetch("load");
            target.setLoadType(source.getLoad().getType());
            target.setLoadKey(source.getLoad().getAssign().getKey());
        }

        FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser).switchComponent(source.getResult().getComponent());
        freeMarkerComponent.setRole("result");
        target.setResultComponent(freeMarkerComponent);
    }
}
