package com.ravijar.model.freemarker;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerPage {
    private String name;
    private String route;
    private String colonRoute;
    private String urlParameter;
    private List<FreeMarkerComponent> components;
}
