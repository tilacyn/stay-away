package com.stayaway.model.actions;

import com.stayaway.model.cards.CardType;
import lombok.Data;

@Data
public class CardChosenAction {
    private final String chooser;
    private final CardType cardType;
}
