package com.ravijar.helper;

import io.swagger.v3.oas.models.PathItem;

public class OpenAPIConverter {
    public static PathItem.HttpMethod getHttpMethod(String method) {
        for (PathItem.HttpMethod httpMethod : PathItem.HttpMethod.values()) {
            if (httpMethod.name().equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        return null;
    }
}
