package com.ravijar.populator;

import com.ravijar.model.freemarker.FreeMarkerHeroSection;
import com.ravijar.model.xml.component.HeroSection;
import com.ravijar.parser.OpenAPIParser;

public class HeroSectionPopulator extends ComponentPopulator{
    public HeroSectionPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(HeroSection source, FreeMarkerHeroSection target) {
        populateComponent(source, target);
        target.setImage(source.getImage().getUrl());
        target.setText(source.getText().getBody());
    }
}
