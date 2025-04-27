package com.ravijar.model.openapi;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OpenAPIResource {
    private String httpMethod;
    private String apiFunctionName;
    private List<OpenAPIParameter> urlParameters;
    private List<OpenAPISchemaProperty> requestProperties;
    private List<OpenAPIResponse> responses;
    private List<OpenAPISecurityRequirement> securityRequirements;
}
