package com.ravijar.helper;

import com.ravijar.model.freemarker.*;
import com.ravijar.model.xml.component.*;
import com.ravijar.parser.OpenAPIParser;
import com.ravijar.populator.*;

public class PopulatorHelper {
    private OpenAPIParser openAPIParser;

    public PopulatorHelper(OpenAPIParser openAPIParser) {
        this.openAPIParser = openAPIParser;
    }

    public FreeMarkerComponent switchComponent(Component component) {
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
            case "CardSection" -> {
                freeMarkerComponent = new FreeMarkerCardSection();
                new CardSectionPopulator(openAPIParser).populate((CardSection) component, (FreeMarkerCardSection) freeMarkerComponent);
            }
            case "Alert" -> {
                freeMarkerComponent = new FreeMarkerAlert();
                new AlertPopulator(openAPIParser).populate((Alert) component, (FreeMarkerAlert) freeMarkerComponent);
            }
            default -> freeMarkerComponent = null;
        }
        return freeMarkerComponent;
    }
}
