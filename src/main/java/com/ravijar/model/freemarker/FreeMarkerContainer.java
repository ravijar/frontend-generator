package com.ravijar.model.freemarker;

import com.ravijar.model.openapi.OpenAPIResource;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerContainer extends FreeMarkerComponent{
    private String fetch;
    private OpenAPIResource resource;
    private String loadType;
    private String loadKey;
    private FreeMarkerComponent resultComponent;
}
