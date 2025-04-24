package com.ravijar.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Deprecated
public class ResponseDTO {
    private String code;
    private String description;
    private String schema;
    private String type;
    private List<String> nextPages;
}
