package com.stayaway.core.action;

import com.stayaway.model.cards.CardType;
import lombok.Data;

@Data
public class DefendAction {
    private final String login;
    private final CardType card;
}
