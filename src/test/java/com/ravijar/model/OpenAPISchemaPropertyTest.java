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

    @Deprecated
    @Test
    void testGetTypeScriptType() {
        OpenAPISchemaProperty integerType = new OpenAPISchemaProperty("id", "integer", "ID");
        assertEquals("number", integerType.getTypeScriptType());

        OpenAPISchemaProperty numberType = new OpenAPISchemaProperty("price", "number", "Price");
        assertEquals("number", numberType.getTypeScriptType());

        OpenAPISchemaProperty stringType = new OpenAPISchemaProperty("name", "string", "Name");
        assertEquals("string", stringType.getTypeScriptType());

        OpenAPISchemaProperty booleanType = new OpenAPISchemaProperty("isActive", "boolean", "Active");
        assertEquals("boolean", booleanType.getTypeScriptType());

        OpenAPISchemaProperty objectType = new OpenAPISchemaProperty("details", "object", "Details");
        assertEquals("{}", objectType.getTypeScriptType());

        OpenAPISchemaProperty arrayType = new OpenAPISchemaProperty("items", "array", "Items");
        assertEquals("any[]", arrayType.getTypeScriptType());

        OpenAPISchemaProperty unknownType = new OpenAPISchemaProperty("custom", "customType", "Custom");
        assertEquals("any", unknownType.getTypeScriptType());
    }
}
