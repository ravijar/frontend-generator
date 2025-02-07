package com.ravijar.model.freemarker;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerButton extends FreeMarkerComponent{
    private String text;
    private String action;
    private String route;
    private String templateLiteralRoute;
    private String urlParameter;
    private String saveType;
    private String saveKey;
}
