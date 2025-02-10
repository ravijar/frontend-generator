package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerForm;
import com.ravijar.model.xml.component.Form;
import com.ravijar.parser.OpenAPIParser;

public class FormPopulator extends ComponentPopulator{
    public FormPopulator(OpenAPIParser openAPIParser) {
        super(openAPIParser);
    }

    public void populate(Form source, FreeMarkerForm target) {
        populateComponent(source, target);
        target.setTitle(source.getText().getBody());
        target.setSubmitText(source.getSubmit().getName());

        if(source.getResource() != null) {
            target.setFetch("resource");
            target.setResource(openAPIParser.getResourceData(source.getResource()));
        }

        FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser).switchComponent(source.getResult().getComponent());
        freeMarkerComponent.setRole("result");
        target.setResultComponent(freeMarkerComponent);
    }
}
