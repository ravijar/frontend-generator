package com.ravijar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Page {
    private String pageName;
    private String resourceUrl;
    private String resourceMethod;
}
