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
    private List<String> urlParameters;
    private List<String> requestParameters;
    private List<OpenAPIResponse> responses;
}
