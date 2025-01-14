package com.ravijar.model.freemarker;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerButton extends FreeMarkerComponent{
    private String text;
    private String route;
}
