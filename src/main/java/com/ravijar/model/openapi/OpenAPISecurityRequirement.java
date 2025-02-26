package com.ravijar.model.openapi;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OpenAPISecurityRequirement {
    private String securitySchemeName;
    private List<String> requiredScopes;
}

