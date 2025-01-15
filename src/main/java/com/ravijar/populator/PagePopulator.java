package com.ravijar.populator;

import com.ravijar.model.freemarker.*;
import com.ravijar.model.xml.Page;
import com.ravijar.model.xml.component.*;
import com.ravijar.parser.OpenAPIParser;

import java.util.ArrayList;
import java.util.List;

public class PagePopulator {
    private OpenAPIParser openAPIParser;

    public PagePopulator(OpenAPIParser openAPIParser) {
        this.openAPIParser = openAPIParser;
    }

    public void populate(Page source, FreeMarkerPage target) {
        target.setName(source.getName());
        target.setRoute(source.getRoute());

        List<FreeMarkerComponent> freeMarkerComponents = new ArrayList<>();

        for (Component component : source.getComponents()) {
            FreeMarkerComponent freeMarkerComponent;
            switch (component.getType()) {
                case "HeroSection" -> {
                    freeMarkerComponent = new FreeMarkerHeroSection();
                    new HeroSectionPopulator(openAPIParser).populate((HeroSection) component, (FreeMarkerHeroSection) freeMarkerComponent);
                }
                case "Button" -> {
                    freeMarkerComponent = new FreeMarkerButton();
                    new ButtonPopulator(openAPIParser).populate((Button) component, (FreeMarkerButton) freeMarkerComponent);
                }
                case "SearchBar" -> {
                    freeMarkerComponent = new FreeMarkerSearchBar();
                    new SearchBarPopulator(openAPIParser).populate((SearchBar) component, (FreeMarkerSearchBar) freeMarkerComponent);
                }
                case "Form" -> {
                    freeMarkerComponent = new FreeMarkerForm();
                    new FormPopulator(openAPIParser).populate((Form) component, (FreeMarkerForm) freeMarkerComponent);
                }
                case "Container" -> {
                    freeMarkerComponent = new FreeMarkerContainer();
                    new ContainerPopulator(openAPIParser).populate((Container) component, (FreeMarkerContainer) freeMarkerComponent);
                }
                default -> freeMarkerComponent = null;
            }
            freeMarkerComponents.add(freeMarkerComponent);
        }
        target.setComponents(freeMarkerComponents);
    }
}
