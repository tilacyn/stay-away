package com.stayaway.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//TODO extend
public enum Card {
    FLAMETHROWER(CardType.EVENT),
    ANALYSIS(CardType.EVENT),
    SUSPICIOUS(CardType.EVENT),
    AXE(CardType.EVENT);
    private final CardType cardType;
}
