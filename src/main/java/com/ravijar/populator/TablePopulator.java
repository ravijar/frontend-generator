package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerTable;
import com.ravijar.model.xml.component.Component;
import com.ravijar.model.xml.component.Table;
import com.ravijar.parser.OpenAPIParser;

import java.util.ArrayList;
import java.util.List;

public class TablePopulator extends ComponentPopulator{
    public TablePopulator(OpenAPIParser openAPIParser) { super(openAPIParser); }

    public void populate(Table source, FreeMarkerTable target) {
        populateComponent(source, target);
        target.setRowKey(source.getAssign().getKey());

        List<FreeMarkerComponent> freeMarkerComponents = new ArrayList<>();

        if(source.getSubComponents() != null) {
            for(Component component : source.getSubComponents()) {
                FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser).switchComponent(component, target);
                freeMarkerComponent.setRole("child");
                freeMarkerComponents.add(freeMarkerComponent);
            }
        }
        target.setSubComponents(freeMarkerComponents);
    }

}
