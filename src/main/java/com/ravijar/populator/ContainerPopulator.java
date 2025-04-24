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
            target.setAction("resource");
            target.setResource(openAPIParser.getResourceData(source.getResource()));
        }

        if(source.getLocalStorage() != null) {
            target.setAction(source.getLocalStorage().getAction());
            target.setLocalStorageKey(source.getLocalStorage().getAssign().getKey());
        }

        FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser).switchComponent(source.getResult().getComponent(), target);
        freeMarkerComponent.setRole("result");
        target.setResultComponent(freeMarkerComponent);
    }
}
