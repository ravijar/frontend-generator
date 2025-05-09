package com.ravijar.populator;

import com.ravijar.helper.PopulatorHelper;
import com.ravijar.model.freemarker.FreeMarkerComponent;
import com.ravijar.model.freemarker.FreeMarkerForm;
import com.ravijar.model.freemarker.FreeMarkerPage;
import com.ravijar.model.openapi.OpenAPIResource;
import com.ravijar.model.xml.Resource;
import com.ravijar.model.xml.component.Form;
import com.ravijar.parser.OpenAPIParser;

public class FormPopulator extends ComponentPopulator{
    public FormPopulator(OpenAPIParser openAPIParser, FreeMarkerPage page) {
        super(openAPIParser, page);
    }

    public void populate(Form source, FreeMarkerForm target) {
        populateComponent(source, target);
        target.setTitle(source.getText().getBody());
        target.setSubmitText(source.getSubmit().getName());

        if(source.getResource() != null) {
            OpenAPIResource openAPIResource = openAPIParser.getResourceData(source.getResource().getUrl(), source.getResource().getMethod());

            target.setAction("resource");
            target.setResource(openAPIResource);

            if(openAPIResource.isSecured()) {
                if (!page.isSecured()) page.setSecured(true);

                for (String scope : openAPIResource.getSecurityRequirements().get(0).getRequiredScopes()) {
                    if (!page.getSecurityScopes().contains(scope)) page.getSecurityScopes().add(scope);
                }
            }

            if (openAPIResource.getHttpMethod().equals("PUT") || openAPIResource.getHttpMethod().equals("PATCH")) {
                target.setFetchResource(openAPIParser.getResourceData(source.getResource().getUrl(), "GET"));
            }
        }

        FreeMarkerComponent freeMarkerComponent = new PopulatorHelper(openAPIParser, page).switchComponent(source.getResult().getComponent(), target);
        freeMarkerComponent.setRole("result");
        target.setResultComponent(freeMarkerComponent);
    }
}
