package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerCardSection;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerPage;
import com.ravijar.model.freemarker.FreeMarkerSearchBar;
import com.ravijar.model.openapi.OpenAPIResource;
import com.ravijar.model.xml.component.CardSection;
import com.ravijar.model.xml.component.SearchBar;
import com.ravijar.parser.OpenAPIParser;

public class SearchBarPopulator extends ComponentPopulator{
    public SearchBarPopulator(OpenAPIParser openAPIParser, FreeMarkerPage page) {
        super(openAPIParser, page);
    }

    public void populate(SearchBar source, FreeMarkerSearchBar target) {
        populateComponent(source, target);

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

        FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser, page).switchComponent(source.getResult().getComponent(), target);
        freeMarkerComponent.setRole("result");
        target.setResultComponent(freeMarkerComponent);
    }
}
