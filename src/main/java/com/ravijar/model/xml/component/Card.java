package com.ravijar.model.xml.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Card extends Component {
    public Card() { this.setType("Card");}
}
