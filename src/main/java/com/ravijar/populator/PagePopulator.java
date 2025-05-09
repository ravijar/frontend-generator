package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.helper.StringHelper;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerPage;
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
        target.setUrlParameter(StringHelper.extractUrlParameter(source.getRoute()));
        target.setColonRoute(StringHelper.toColonRoute(source.getRoute()));
        target.setSecured(false);
        target.setVisibleInNavBar(source.isNavbar());
        target.setSecurityScopes(new ArrayList<>());

        List<FreeMarkerComponent> freeMarkerComponents = new ArrayList<>();

        for(Component component : source.getComponents()) {
            FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser, target).switchComponent(component, null);
            freeMarkerComponent.setRole("root");
            freeMarkerComponents.add(freeMarkerComponent);
        }
        target.setComponents(freeMarkerComponents);
    }
}
