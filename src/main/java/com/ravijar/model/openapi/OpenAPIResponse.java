package com.ravijar.model.openapi;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OpenAPIResponse {
    private String code;
    private String schemaName;
    private String type;
    private String description;
    private List<OpenAPISchemaProperty> schemaProperties;
}