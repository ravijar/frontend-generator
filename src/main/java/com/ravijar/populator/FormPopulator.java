package com.ravijar.populator;

import com.ravijar.helper.PopulatorSwitch;
import com.ravijar.model.freemarker.FreeMarkerForm;
import com.ravijar.model.xml.component.Form;
import com.ravijar.parser.OpenAPIParser;

public class FormPopulator extends ComponentPopulator{
    public FormPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(Form source, FreeMarkerForm target) {
        populateComponent(source, target);
        target.setResource(openAPIParser.getResourceData(source.getResource()));
        target.setSubmitText(source.getSubmit().getName());
        target.setResultComponent(new PopulatorSwitch(openAPIParser).switchComponent(source.getResult().getComponent()));
    }
}
