package com.ravijar.model.freemarker;

import com.ravijar.model.openapi.OpenAPIResource;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerForm extends FreeMarkerComponent{
    private String title;
    private String submitText;
    private String action;
    private OpenAPIResource resource;
    private FreeMarkerComponent resultComponent;
    private OpenAPIResource fetchResource;
}
