package com.ravijar.populator;

import com.ravijar.helper.StringHelper;
import com.ravijar.model.freemarker.FreeMarkerCardSection;
import com.ravijar.model.xml.component.CardSection;
import com.ravijar.parser.OpenAPIParser;

public class CardSectionPopulator extends ComponentPopulator{
    public CardSectionPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(CardSection source, FreeMarkerCardSection target) {
        populateComponent(source, target);
        target.setCardKey(source.getAssign().getKey());
        target.setCardTitle(source.getAssign().getTitle());
        target.setCardDescription(source.getAssign().getDescription());
        target.setCardImage(source.getAssign().getImage());
        target.setRoute(source.getRoute().getUrl());
        target.setTemplateLiteralRoute(StringHelper.toTemplateLiteralRoute(source.getRoute().getUrl()));
        target.setUrlParameter(StringHelper.extractUrlParameter(source.getRoute().getUrl()));
    }
}
