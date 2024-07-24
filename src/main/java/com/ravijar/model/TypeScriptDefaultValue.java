package com.ravijar.model;

public enum TypeScriptDefaultValue {
    NUMBER("0"),
    STRING("\"\""),
    BOOLEAN("false"),
    OBJECT("{}"),
    ARRAY("[]"),
    ANY("null");

    private final String defaultValue;

    TypeScriptDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public static String getDefaultValueForType(String typeScriptType) {
        switch (typeScriptType) {
            case "number":
                return NUMBER.getDefaultValue();
            case "string":
                return STRING.getDefaultValue();
            case "boolean":
                return BOOLEAN.getDefaultValue();
            case "{}":
                return OBJECT.getDefaultValue();
            case "any[]":
                return ARRAY.getDefaultValue();
            default:
                return ANY.getDefaultValue();
        }
    }
}

