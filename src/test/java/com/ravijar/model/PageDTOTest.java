package com.ravijar.model;

import io.swagger.v3.oas.models.PathItem.HttpMethod;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@Deprecated
class PageDTOTest {

    @Test
    void testGettersAndSetters() {
        String pageName = "HomePage";
        String resourceUrl = "/home";
        HttpMethod resourceMethod = HttpMethod.GET;
        boolean customStyled = true;
        String pageTitle = "Welcome to Home Page";

        PageDTO pageDTO = new PageDTO(pageName, resourceUrl, resourceMethod, customStyled, pageTitle);

        assertEquals(pageName, pageDTO.getPageName());
        assertEquals(resourceUrl, pageDTO.getResourceUrl());
        assertEquals(resourceMethod, pageDTO.getResourceMethod());
        assertTrue(pageDTO.isCustomStyled());
        assertEquals(pageTitle, pageDTO.getPageTitle());
    }

    @Test
    void testToString() {
        String pageName = "HomePage";
        String resourceUrl = "/home";
        HttpMethod resourceMethod = HttpMethod.GET;
        boolean customStyled = true;
        String pageTitle = "Welcome to Home Page";

        PageDTO pageDTO = new PageDTO(pageName, resourceUrl, resourceMethod, customStyled, pageTitle);

        String result = pageDTO.toString();

        assertTrue(result.contains(pageName));
        assertTrue(result.contains(resourceUrl));
        assertTrue(result.contains(resourceMethod.toString()));
        assertTrue(result.contains(String.valueOf(customStyled)));
        assertTrue(result.contains(pageTitle));
    }

    @Test
    void testEqualsAndHashCode() {
        String pageName = "HomePage";
        String resourceUrl = "/home";
        HttpMethod resourceMethod = HttpMethod.GET;
        boolean customStyled = true;
        String pageTitle = "Welcome to Home Page";

        PageDTO pageDTO1 = new PageDTO(pageName, resourceUrl, resourceMethod, customStyled, pageTitle);
        PageDTO pageDTO2 = new PageDTO(pageName, resourceUrl, resourceMethod, customStyled, pageTitle);

        assertEquals(pageDTO1, pageDTO2);
        assertEquals(pageDTO1.hashCode(), pageDTO2.hashCode());
    }
}

