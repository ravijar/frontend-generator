package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.helper.StringHelper;
import com.ravijar.model.freemarker.FreeMarkerButton;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerPage;
import com.ravijar.model.openapi.OpenAPIResource;
import com.ravijar.model.xml.Property;
import com.ravijar.model.xml.component.Button;
import com.ravijar.parser.OpenAPIParser;

import java.util.*;

public class ButtonPopulator extends ComponentPopulator{
    public ButtonPopulator(OpenAPIParser openAPIParser, FreeMarkerPage page) {
        super(openAPIParser, page);
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
            OpenAPIResource openAPIResource = openAPIParser.getResourceData(source.getResource().getUrl(), source.getResource().getMethod());

            target.setAction("resource");
            target.setResource(openAPIResource);

            if(source.getModel() != null) {
                List<Map<String, String>> properties = new ArrayList<>();

                for (Property propertyItem : source.getModel().getProperties()) {
                    Map<String, String> property = new HashMap<>();
                    property.put(propertyItem.getKey(), propertyItem.getValue());
                    properties.add(property);
                }

                target.setModelProperties(properties);
            }

            if(openAPIResource.isSecured()) {
                if (!page.isSecured()) page.setSecured(true);

                for (String scope : openAPIResource.getSecurityRequirements().get(0).getRequiredScopes()) {
                    if (!page.getSecurityScopes().contains(scope)) page.getSecurityScopes().add(scope);
                }
            }
        }

        if(source.getResult() != null) {
            FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser, page).switchComponent(source.getResult().getComponent(), target);
            freeMarkerComponent.setRole("result");
            target.setResultComponent(freeMarkerComponent);
        }
    }
}
