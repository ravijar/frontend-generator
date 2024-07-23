package com.ravijar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProperty {
    private String property;
    private String type;

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
