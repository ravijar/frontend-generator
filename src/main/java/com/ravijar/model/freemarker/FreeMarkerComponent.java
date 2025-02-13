package com.ravijar.model.freemarker;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class FreeMarkerComponent {
    private String type;
    private String id;
    private String styleId;
    private String role;
    private FreeMarkerComponent parent;
}
