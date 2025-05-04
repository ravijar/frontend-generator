package com.ravijar.model.freemarker;

import com.ravijar.model.openapi.OpenAPIResource;
import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerButton extends FreeMarkerComponent{
    private String text;
    private String action;
    private String route;
    private String templateLiteralRoute;
    private String urlParameter;
    private String localStorageKey;
    private OpenAPIResource resource;
    private FreeMarkerComponent resultComponent;
    private List<Map<String, String>> modelProperties;
}
