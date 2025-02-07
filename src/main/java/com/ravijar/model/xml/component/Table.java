package com.ravijar.model.xml.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Table extends Component{
    public Table() { this.setType("Table"); }
}
