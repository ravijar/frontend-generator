package com.ravijar.model.openapi;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OpenAPISchemaProperty {
    private String name;
    private String type;
    private String displayName;
}
