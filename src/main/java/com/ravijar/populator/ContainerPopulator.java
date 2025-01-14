package com.ravijar.populator;

import com.ravijar.model.freemarker.FreeMarkerCardSection;
import com.ravijar.model.freemarker.FreeMarkerContainer;
import com.ravijar.model.xml.component.CardSection;
import com.ravijar.model.xml.component.Container;
import com.ravijar.parser.OpenAPIParser;

public class ContainerPopulator extends ComponentPopulator{
    public ContainerPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(Container source, FreeMarkerContainer target) {
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
