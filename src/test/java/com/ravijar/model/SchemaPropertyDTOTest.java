package com.ravijar.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchemaPropertyDTOTest {

    @Test
    void testGettersAndSetters() {
        String name = "id";
        String type = "integer";
        String displayName = "ID";

        SchemaPropertyDTO schemaPropertyDTO = new SchemaPropertyDTO(name, type, displayName);

        assertEquals(name, schemaPropertyDTO.getName());
        assertEquals(type, schemaPropertyDTO.getType());
        assertEquals(displayName, schemaPropertyDTO.getDisplayName());
    }

    @Test
    void testToString() {
        String name = "id";
        String type = "integer";
        String displayName = "ID";

        SchemaPropertyDTO schemaPropertyDTO = new SchemaPropertyDTO(name, type, displayName);

        String result = schemaPropertyDTO.toString();

        assertTrue(result.contains(name));
        assertTrue(result.contains(type));
        assertTrue(result.contains(displayName));
    }

    @Test
    void testEqualsAndHashCode() {
        String name = "id";
        String type = "integer";
        String displayName = "ID";

        SchemaPropertyDTO schemaPropertyDTO1 = new SchemaPropertyDTO(name, type, displayName);
        SchemaPropertyDTO schemaPropertyDTO2 = new SchemaPropertyDTO(name, type, displayName);

        assertEquals(schemaPropertyDTO1, schemaPropertyDTO2);
        assertEquals(schemaPropertyDTO1.hashCode(), schemaPropertyDTO2.hashCode());
    }

    @Test
    void testGetTypeScriptType() {
        SchemaPropertyDTO integerType = new SchemaPropertyDTO("id", "integer", "ID");
        assertEquals("number", integerType.getTypeScriptType());

        SchemaPropertyDTO numberType = new SchemaPropertyDTO("price", "number", "Price");
        assertEquals("number", numberType.getTypeScriptType());

        SchemaPropertyDTO stringType = new SchemaPropertyDTO("name", "string", "Name");
        assertEquals("string", stringType.getTypeScriptType());

        SchemaPropertyDTO booleanType = new SchemaPropertyDTO("isActive", "boolean", "Active");
        assertEquals("boolean", booleanType.getTypeScriptType());

        SchemaPropertyDTO objectType = new SchemaPropertyDTO("details", "object", "Details");
        assertEquals("{}", objectType.getTypeScriptType());

        SchemaPropertyDTO arrayType = new SchemaPropertyDTO("items", "array", "Items");
        assertEquals("any[]", arrayType.getTypeScriptType());

        SchemaPropertyDTO unknownType = new SchemaPropertyDTO("custom", "customType", "Custom");
        assertEquals("any", unknownType.getTypeScriptType());
    }
}
