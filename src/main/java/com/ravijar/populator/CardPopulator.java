package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerCard;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerPage;
import com.ravijar.model.xml.component.Card;
import com.ravijar.model.xml.component.Component;
import com.ravijar.parser.OpenAPIParser;

import java.util.ArrayList;
import java.util.List;

public class CardPopulator extends ComponentPopulator{
    public CardPopulator(OpenAPIParser openAPIParser, FreeMarkerPage page) {
        super(openAPIParser, page);
    }

    public void populate(Card source, FreeMarkerCard target) {
        populateComponent(source, target);
        target.setCardKey(source.getAssign().getKey());
        target.setCardTitle(source.getAssign().getTitle());
        target.setCardDescription(source.getAssign().getDescription());
        target.setCardImage(source.getAssign().getImage());

        List<FreeMarkerComponent> freeMarkerComponents = new ArrayList<>();

        if(source.getSubComponents() != null) {
            for(Component component : source.getSubComponents()) {
                FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser, page).switchComponent(component, target);
                freeMarkerComponent.setRole("child");
                freeMarkerComponents.add(freeMarkerComponent);
            }
        }
        target.setSubComponents(freeMarkerComponents);
    }
}
