package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerContainer;
import com.ravijar.model.freemarker.FreeMarkerPage;
import com.ravijar.model.openapi.OpenAPIResource;
import com.ravijar.model.xml.component.Container;
import com.ravijar.parser.OpenAPIParser;

public class ContainerPopulator extends ComponentPopulator{
    public ContainerPopulator(OpenAPIParser openAPIParser, FreeMarkerPage page) {
        super(openAPIParser, page);
    }

    public void populate(Container source, FreeMarkerContainer target) {
        populateComponent(source, target);

        if(source.getText() != null && !source.getText().getBody().isEmpty()) {
            target.setTitle(source.getText().getBody());
        }

        if(source.getResource() != null) {
            OpenAPIResource openAPIResource = openAPIParser.getResourceData(source.getResource().getUrl(), source.getResource().getMethod());

            target.setAction("resource");
            target.setResource(openAPIResource);

            if(openAPIResource.isSecured()) {
                if (!page.isSecured()) page.setSecured(true);

                for (String scope : openAPIResource.getSecurityRequirements().get(0).getRequiredScopes()) {
                    if (!page.getSecurityScopes().contains(scope)) page.getSecurityScopes().add(scope);
                }
            }
        }

        if(source.getLocalStorage() != null) {
            target.setAction(source.getLocalStorage().getAction());
            target.setLocalStorageKey(source.getLocalStorage().getAssign().getKey());
        }

        FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser, page).switchComponent(source.getResult().getComponent(), target);
        freeMarkerComponent.setRole("result");
        target.setResultComponent(freeMarkerComponent);
    }
}
