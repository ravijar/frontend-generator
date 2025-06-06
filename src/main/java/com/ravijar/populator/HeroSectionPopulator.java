package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerHeroSection;
import com.ravijar.model.freemarker.FreeMarkerPage;
import com.ravijar.model.xml.component.Component;
import com.ravijar.model.xml.component.HeroSection;
import com.ravijar.parser.OpenAPIParser;

import java.util.ArrayList;
import java.util.List;

public class HeroSectionPopulator extends ComponentPopulator{
    public HeroSectionPopulator(OpenAPIParser openAPIParser, FreeMarkerPage page) {
        super(openAPIParser, page);
    }

    public void populate(HeroSection source, FreeMarkerHeroSection target) {
        populateComponent(source, target);
        target.setImage(source.getImage().getUrl());
        target.setText(source.getTexts().get(0).getBody());
        target.setSubtext(source.getTexts().get(1).getBody());

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
