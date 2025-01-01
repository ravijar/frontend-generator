package com.ravijar.model.freemarker;

import com.ravijar.model.openapi.OpenAPIResource;
import com.ravijar.model.xml.component.Component;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FreeMarkerComponent {
    private String id;
    private Component body;
    private OpenAPIResource resource;
}
