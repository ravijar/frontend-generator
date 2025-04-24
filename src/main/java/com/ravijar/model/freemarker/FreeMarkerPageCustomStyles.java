package com.ravijar.model.freemarker;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Deprecated
public class FreeMarkerPageCustomStyles {
    private String pageName;
    private List<String> components;
}
