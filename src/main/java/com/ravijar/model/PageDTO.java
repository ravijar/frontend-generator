package com.ravijar.model;

import io.swagger.v3.oas.models.PathItem;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Deprecated
public class PageDTO {
    private String pageName;
    private String resourceUrl;
    private PathItem.HttpMethod resourceMethod;
    private boolean customStyled;
    private String pageTitle;
}
