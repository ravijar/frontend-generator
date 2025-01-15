package com.ravijar.model.xml.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Alert extends Component{
    public Alert() {
        this.setType("Alert");
    }
}
