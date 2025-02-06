package com.ravijar.model.freemarker;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerHeroSection extends FreeMarkerComponent{
    private String text;
    private String subtext;
    private String image;
    private List<FreeMarkerComponent> subComponents;
}
