package com.ravijar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseDTO {
    private String code;
    private String description;
    private String schema;
    private String type;
    private List<String> nextPages;
}
