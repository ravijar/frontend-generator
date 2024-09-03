package com.ravijar.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SchemaPropertyDTO {
    private String name;
    private String type;
    private String displayName;

    public String getTypeScriptType() {
        switch (type) {
            case "integer":
                return "number";
            case "number":
                return "number";
            case "string":
                return "string";
            case "boolean":
                return "boolean";
            case "object":
                return "{}";
            case "array":
                return "any[]";
            default:
                return "any";
        }
    }
}
