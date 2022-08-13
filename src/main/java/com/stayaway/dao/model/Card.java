package com.stayaway.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//TODO extend
public enum Card {
    THE_THING(CardType.EVENT),
    INFECTED(CardType.EVENT),
    FLAMETHROWER(CardType.EVENT),
    ANALYSIS(CardType.EVENT),
    SUSPICIOUS(CardType.EVENT),
    AXE(CardType.EVENT),
    WHISKEY(CardType.EVENT),
    RESOLUTE(CardType.EVENT),
    WATCH_YOUR_BACK(CardType.EVENT),
    CHANGE_PLACES(CardType.EVENT),
    YOU_BETTER_RUN(CardType.EVENT),
    SEDUCTION(CardType.EVENT),
    SCARY(CardType.EVENT),
    IM_COMFORTABLE(CardType.EVENT),
    NO_THANKS(CardType.EVENT),
    MISSED(CardType.EVENT),
    NO_BARBECUE(CardType.EVENT),
    QUARANTINE(CardType.EVENT),
    BARRED_DOOR(CardType.EVENT);

    private final CardType cardType;
}
