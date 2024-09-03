package com.ravijar.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterDTOTest {

    @Test
    void testGettersAndSetters() {
        String name = "paramName";
        String displayName = "Parameter Name";

        ParameterDTO parameterDTO = new ParameterDTO(name, displayName);

        assertEquals(name, parameterDTO.getName());
        assertEquals(displayName, parameterDTO.getDisplayName());
    }

    @Test
    void testToString() {
        String name = "paramName";
        String displayName = "Parameter Name";

        ParameterDTO parameterDTO = new ParameterDTO(name, displayName);

        String result = parameterDTO.toString();

        assertTrue(result.contains(name));
        assertTrue(result.contains(displayName));
    }

    @Test
    void testEqualsAndHashCode() {
        String name = "paramName";
        String displayName = "Parameter Name";

        ParameterDTO parameterDTO1 = new ParameterDTO(name, displayName);
        ParameterDTO parameterDTO2 = new ParameterDTO(name, displayName);

        assertEquals(parameterDTO1, parameterDTO2);
        assertEquals(parameterDTO1.hashCode(), parameterDTO2.hashCode());
    }
}
