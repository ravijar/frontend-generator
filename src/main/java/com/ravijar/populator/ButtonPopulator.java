package com.ravijar.populator;

import com.ravijar.helper.StringHelper;
import com.ravijar.model.freemarker.FreeMarkerButton;
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

        if (source.getSave() != null) {
            target.setAction("save");
            target.setSaveType(source.getSave().getType());
            target.setSaveKey(source.getSave().getAssign().getKey());
        }
    }
}
