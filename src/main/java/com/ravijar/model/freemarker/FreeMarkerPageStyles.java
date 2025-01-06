package com.ravijar.model.freemarker;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FreeMarkerPageStyles {
    private String pageName;
    private List<String> classes;
}
