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
        target.setCardKey(source.getData().getKey());
        target.setCardTitle(source.getData().getTitle());
        target.setCardDescription(source.getData().getDescription());
        target.setCardImage(source.getData().getImage());
    }
}
