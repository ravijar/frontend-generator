package com.ravijar.model.freemarker;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FreeMarkerPage {
    private String name;
    private String route;
    private List<FreeMarkerComponent> components;
}
