package com.ravijar.model.freemarker;

import com.ravijar.model.openapi.OpenAPIResource;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerContainer extends FreeMarkerComponent{
    private String title;
    private String action;
    private OpenAPIResource resource;
    private String localStorageKey;
    private FreeMarkerComponent resultComponent;
}
