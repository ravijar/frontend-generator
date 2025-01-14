package com.ravijar.populator;

import com.ravijar.model.freemarker.FreeMarkerAlert;
import com.ravijar.model.freemarker.FreeMarkerForm;
import com.ravijar.model.xml.component.Alert;
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

        switch (source.getResult().getComponent().getType()) {
            case "Alert":
                FreeMarkerAlert freeMarkerAlert = new FreeMarkerAlert();
                new AlertPopulator(openAPIParser).populate((Alert) source.getResult().getComponent(), freeMarkerAlert);
                target.setResultComponent(freeMarkerAlert);
                break;
        }
    }
}
