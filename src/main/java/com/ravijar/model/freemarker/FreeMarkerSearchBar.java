package com.ravijar.model.freemarker;

import com.ravijar.model.openapi.OpenAPIResource;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerSearchBar extends FreeMarkerComponent{
    private OpenAPIResource resource;
    private FreeMarkerComponent resultComponent;
}
