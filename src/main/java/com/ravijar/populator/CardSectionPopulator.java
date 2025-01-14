package com.ravijar.populator;

import com.ravijar.model.freemarker.FreeMarkerButton;
import com.ravijar.model.freemarker.FreeMarkerCardSection;
import com.ravijar.model.xml.component.Button;
import com.ravijar.model.xml.component.CardSection;
import com.ravijar.model.xml.component.Component;
import com.ravijar.parser.OpenAPIParser;

public class CardSectionPopulator extends ComponentPopulator{
    public CardSectionPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(CardSection source, FreeMarkerCardSection target) {
        populateComponent(source, target);

        for(Component component : source.getComponents()) {
            switch (component.getType()) {
                case "Button":
                    FreeMarkerButton freeMarkerButton = new FreeMarkerButton();
                    new ButtonPopulator(openAPIParser).populate((Button) component, freeMarkerButton);
                    target.getSubComponents().add(freeMarkerButton);
                    break;
            }
        }
    }
}
