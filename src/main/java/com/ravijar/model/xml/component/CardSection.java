package com.ravijar.model.xml.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardSection extends Component {
    public CardSection() { this.setType("CardSection");}
}
