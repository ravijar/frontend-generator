package com.ravijar.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Deprecated
class ResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        String code = "200";
        String description = "Success";
        String schema = "UserSchema";
        String type = "application/json";
        List<String> nextPages = Arrays.asList("HomePage", "Dashboard");

        ResponseDTO responseDTO = new ResponseDTO(code, description, schema, type, nextPages);

        assertEquals(code, responseDTO.getCode());
        assertEquals(description, responseDTO.getDescription());
        assertEquals(schema, responseDTO.getSchema());
        assertEquals(type, responseDTO.getType());
        assertEquals(nextPages, responseDTO.getNextPages());
    }

    @Test
    void testToString() {
        String code = "200";
        String description = "Success";
        String schema = "UserSchema";
        String type = "application/json";
        List<String> nextPages = Arrays.asList("HomePage", "Dashboard");

        ResponseDTO responseDTO = new ResponseDTO(code, description, schema, type, nextPages);

        String result = responseDTO.toString();

        assertTrue(result.contains(code));
        assertTrue(result.contains(description));
        assertTrue(result.contains(schema));
        assertTrue(result.contains(type));
        assertTrue(result.contains(nextPages.toString()));
    }

    @Test
    void testEqualsAndHashCode() {
        String code = "200";
        String description = "Success";
        String schema = "UserSchema";
        String type = "application/json";
        List<String> nextPages = Arrays.asList("HomePage", "Dashboard");

        ResponseDTO responseDTO1 = new ResponseDTO(code, description, schema, type, nextPages);
        ResponseDTO responseDTO2 = new ResponseDTO(code, description, schema, type, nextPages);

        assertEquals(responseDTO1, responseDTO2);
        assertEquals(responseDTO1.hashCode(), responseDTO2.hashCode());
    }
}
