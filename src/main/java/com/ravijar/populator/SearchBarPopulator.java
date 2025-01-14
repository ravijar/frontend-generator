package com.ravijar.populator;

import com.ravijar.model.freemarker.FreeMarkerCardSection;
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
        target.setResource(openAPIParser.getResourceData(source.getResource()));

        switch (source.getResult().getComponent().getType()) {
            case "CardSection":
                FreeMarkerCardSection freeMarkerCardSection = new FreeMarkerCardSection();
                new CardSectionPopulator(openAPIParser).populate((CardSection) source.getResult().getComponent(), freeMarkerCardSection);
                target.setResultComponent(freeMarkerCardSection);
                break;
        }
    }
}
