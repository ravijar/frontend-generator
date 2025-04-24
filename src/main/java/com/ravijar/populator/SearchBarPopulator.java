package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerCardSection;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerSearchBar;
import com.ravijar.model.xml.component.CardSection;
import com.ravijar.model.xml.component.SearchBar;
import com.ravijar.parser.OpenAPIParser;

public class SearchBarPopulator extends ComponentPopulator{
    public SearchBarPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(SearchBar source, FreeMarkerSearchBar target) {
        populateComponent(source, target);

        if(source.getResource() != null) {
            target.setAction("resource");
            target.setResource(openAPIParser.getResourceData(source.getResource()));
        }

        FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser).switchComponent(source.getResult().getComponent(), target);
        freeMarkerComponent.setRole("result");
        target.setResultComponent(freeMarkerComponent);
    }
}
