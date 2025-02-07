package com.ravijar.populator;

import com.ravijar.model.freemarker.FreeMarkerTable;
import com.ravijar.model.xml.component.Table;
import com.ravijar.parser.OpenAPIParser;

public class TablePopulator extends ComponentPopulator{
    public TablePopulator(OpenAPIParser openAPIParser) { super(openAPIParser); }

    public void populate(Table source, FreeMarkerTable target) {
        populateComponent(source, target);
    }

}
