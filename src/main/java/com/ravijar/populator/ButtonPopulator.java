package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.helper.StringHelper;
import com.ravijar.model.freemarker.FreeMarkerButton;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.xml.component.Button;
import com.ravijar.parser.OpenAPIParser;

public class ButtonPopulator extends ComponentPopulator{
    public ButtonPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(Button source, FreeMarkerButton target) {
        populateComponent(source, target);
        target.setText(source.getText().getBody());

        if (source.getRoute() != null) {
            target.setAction("navigate");
            target.setRoute(source.getRoute().getUrl());
            target.setUrlParameter(StringHelper.extractUrlParameter(source.getRoute().getUrl()));
            target.setTemplateLiteralRoute(StringHelper.toTemplateLiteralRoute(source.getRoute().getUrl()));
        }

        if (source.getLocalStorage() != null) {
            target.setAction(source.getLocalStorage().getAction());
            target.setLocalStorageKey(source.getLocalStorage().getAssign().getKey());
        }

        if(source.getResource() != null) {
            target.setAction("resource");
            target.setResource(openAPIParser.getResourceData(source.getResource()));
        }

        if(source.getResult() != null) {
            FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser).switchComponent(source.getResult().getComponent(), target);
            freeMarkerComponent.setRole("result");
            target.setResultComponent(freeMarkerComponent);
        }
    }
}
