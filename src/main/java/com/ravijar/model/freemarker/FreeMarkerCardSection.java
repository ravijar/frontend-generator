package com.ravijar.model.freemarker;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerCardSection extends FreeMarkerComponent{
    private String cardKey;
    private String cardTitle;
    private String cardDescription;
    private String cardImage;
    private String cardHighlight;
    private String route;
    private String templateLiteralRoute;
    private String urlParameter;
}
