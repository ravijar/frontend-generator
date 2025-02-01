package com.ravijar.model;

import com.ravijar.model.openapi.OpenAPIParameter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class OpenAPIParameterTest {

    @Test
    void testGettersAndSetters() {
        String name = "paramName";
        String displayName = "Parameter Name";

        OpenAPIParameter openAPIParameter = new OpenAPIParameter(name, displayName);

        assertEquals(name, openAPIParameter.getName());
        assertEquals(displayName, openAPIParameter.getDisplayName());
    }

    @Test
    void testToString() {
        String name = "paramName";
        String displayName = "Parameter Name";

        OpenAPIParameter openAPIParameter = new OpenAPIParameter(name, displayName);

        String result = openAPIParameter.toString();

        assertTrue(result.contains(name));
        assertTrue(result.contains(displayName));
    }

    @Test
    void testEqualsAndHashCode() {
        String name = "paramName";
        String displayName = "Parameter Name";

        OpenAPIParameter openAPIParameter1 = new OpenAPIParameter(name, displayName);
        OpenAPIParameter openAPIParameter2 = new OpenAPIParameter(name, displayName);

        assertEquals(openAPIParameter1, openAPIParameter2);
        assertEquals(openAPIParameter1.hashCode(), openAPIParameter2.hashCode());
    }
}
