package com.ravijar.model;

import com.ravijar.model.openapi.OpenAPISchemaProperty;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class OpenAPISchemaPropertyTest {

    @Test
    void testGettersAndSetters() {
        String name = "id";
        String type = "integer";
        String displayName = "ID";

        OpenAPISchemaProperty openAPISchemaProperty = new OpenAPISchemaProperty(name, type, displayName);

        assertEquals(name, openAPISchemaProperty.getName());
        assertEquals(type, openAPISchemaProperty.getType());
        assertEquals(displayName, openAPISchemaProperty.getDisplayName());
    }

    @Test
    void testToString() {
        String name = "id";
        String type = "integer";
        String displayName = "ID";

        OpenAPISchemaProperty openAPISchemaProperty = new OpenAPISchemaProperty(name, type, displayName);

        String result = openAPISchemaProperty.toString();

        assertTrue(result.contains(name));
        assertTrue(result.contains(type));
        assertTrue(result.contains(displayName));
    }

    @Test
    void testEqualsAndHashCode() {
        String name = "id";
        String type = "integer";
        String displayName = "ID";

        OpenAPISchemaProperty openAPISchemaProperty1 = new OpenAPISchemaProperty(name, type, displayName);
        OpenAPISchemaProperty openAPISchemaProperty2 = new OpenAPISchemaProperty(name, type, displayName);

        assertEquals(openAPISchemaProperty1, openAPISchemaProperty2);
        assertEquals(openAPISchemaProperty1.hashCode(), openAPISchemaProperty2.hashCode());
    }
}
