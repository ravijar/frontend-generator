package com.ravijar.model.openapi;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OpenAPIResponse {
    private String code;
    private String schema;
    private String type;
}
