package com.ravijar.model;

import io.swagger.v3.oas.models.PathItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageDTO {
    private String pageName;
    private String resourceUrl;
    private PathItem.HttpMethod resourceMethod;
    private boolean customStyled;
    private String pageTitle;
}
