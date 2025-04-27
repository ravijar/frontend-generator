package com.ravijar.helper;

import com.ravijar.model.freemarker.*;
import com.ravijar.model.xml.component.*;
import com.ravijar.parser.OpenAPIParser;
import com.ravijar.populator.*;

public class PopulatorHelper {
    private final OpenAPIParser openAPIParser;
    private final FreeMarkerPage page;

    public PopulatorHelper(OpenAPIParser openAPIParser, FreeMarkerPage page) {
        this.openAPIParser = openAPIParser;
        this.page = page;
    }

    public FreeMarkerComponent switchComponent(Component component, FreeMarkerComponent parentComponent) {
        FreeMarkerComponent freeMarkerComponent;
        switch (component.getType()) {
            case "HeroSection" -> {
                freeMarkerComponent = new FreeMarkerHeroSection();
                new HeroSectionPopulator(openAPIParser, page).populate((HeroSection) component, (FreeMarkerHeroSection) freeMarkerComponent);
            }
            case "Button" -> {
                freeMarkerComponent = new FreeMarkerButton();
                new ButtonPopulator(openAPIParser, page).populate((Button) component, (FreeMarkerButton) freeMarkerComponent);
            }
            case "SearchBar" -> {
                freeMarkerComponent = new FreeMarkerSearchBar();
                new SearchBarPopulator(openAPIParser, page).populate((SearchBar) component, (FreeMarkerSearchBar) freeMarkerComponent);
            }
            case "Form" -> {
                freeMarkerComponent = new FreeMarkerForm();
                new FormPopulator(openAPIParser, page).populate((Form) component, (FreeMarkerForm) freeMarkerComponent);
            }
            case "Container" -> {
                freeMarkerComponent = new FreeMarkerContainer();
                new ContainerPopulator(openAPIParser, page).populate((Container) component, (FreeMarkerContainer) freeMarkerComponent);
            }
            case "CardSection" -> {
                freeMarkerComponent = new FreeMarkerCardSection();
                new CardSectionPopulator(openAPIParser, page).populate((CardSection) component, (FreeMarkerCardSection) freeMarkerComponent);
            }
            case "Alert" -> {
                freeMarkerComponent = new FreeMarkerAlert();
                new AlertPopulator(openAPIParser, page).populate((Alert) component, (FreeMarkerAlert) freeMarkerComponent);
            }
            case "Card" -> {
                freeMarkerComponent = new FreeMarkerCard();
                new CardPopulator(openAPIParser, page).populate((Card) component, (FreeMarkerCard) freeMarkerComponent);
            }
            case "Table" -> {
                freeMarkerComponent = new FreeMarkerTable();
                new TablePopulator(openAPIParser, page).populate((Table) component, (FreeMarkerTable) freeMarkerComponent);
            }
            default -> {
                return null;
            }
        }
        freeMarkerComponent.setParent(parentComponent);
        return freeMarkerComponent;
    }
}
