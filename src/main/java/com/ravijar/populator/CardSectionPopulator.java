package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
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
            FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser).switchComponent(component);
            freeMarkerComponent.setRole("child");
            freeMarkerComponents.add(freeMarkerComponent);
        }
        target.setSubComponents(freeMarkerComponents);
    }
}
