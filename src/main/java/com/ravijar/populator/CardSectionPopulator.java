package com.ravijar.populator;

import com.ravijar.helper.PopulatorSwitch;
import com.ravijar.model.freemarker.FreeMarkerCardSection;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.xml.component.CardSection;
import com.ravijar.model.xml.component.Component;
import com.ravijar.parser.OpenAPIParser;

import java.util.ArrayList;
import java.util.List;

public class CardSectionPopulator extends ComponentPopulator{
    public CardSectionPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(CardSection source, FreeMarkerCardSection target) {
        populateComponent(source, target);

        List<FreeMarkerComponent> freeMarkerComponents = new ArrayList<>();

        for(Component component : source.getComponents()) {
            freeMarkerComponents.add(new PopulatorSwitch(openAPIParser).switchComponent(component));
        }
        target.setSubComponents(freeMarkerComponents);
    }
}
