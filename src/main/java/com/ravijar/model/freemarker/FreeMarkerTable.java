package com.ravijar.model.freemarker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FreeMarkerTable extends FreeMarkerComponent {
    private String rowKey;
    private List<FreeMarkerComponent> subComponents;
}
