package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerCard;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.xml.component.Card;
import com.ravijar.model.xml.component.Component;
import com.ravijar.parser.OpenAPIParser;

import java.util.ArrayList;
import java.util.List;

public class CardPopulator extends ComponentPopulator{
    public CardPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(Card source, FreeMarkerCard target) {
        populateComponent(source, target);

        List<FreeMarkerComponent> freeMarkerComponents = new ArrayList<>();

        if(source.getSubComponents() != null) {
            for(Component component : source.getSubComponents()) {
                FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser).switchComponent(component);
                freeMarkerComponent.setRole("child");
                freeMarkerComponents.add(freeMarkerComponent);
            }
        }
        target.setSubComponents(freeMarkerComponents);
    }
}
