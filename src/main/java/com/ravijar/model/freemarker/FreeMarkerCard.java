package com.ravijar.model.freemarker;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerCard extends FreeMarkerComponent{
    private String cardTitle;
    private String cardDescription;
    private String cardImage;
    private List<FreeMarkerComponent> subComponents;
}