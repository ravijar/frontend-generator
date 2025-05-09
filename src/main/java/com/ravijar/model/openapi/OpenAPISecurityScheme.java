package com.ravijar.model.openapi;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OpenAPISecurityScheme {
    private String type;
    private String authorizationUrl;
    private String tokenUrl;
    private Map<String, String> scopes;
}

