package com.stayaway.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//TODO extend
public enum CardType {
    THE_THING(CardKind.EVENT),
    INFECTED(CardKind.EVENT),
    FLAMETHROWER(CardKind.EVENT),
    ANALYSIS(CardKind.EVENT),
    SUSPICIOUS(CardKind.EVENT),
    AXE(CardKind.EVENT),
    WHISKEY(CardKind.EVENT),
    RESOLUTE(CardKind.EVENT),
    WATCH_YOUR_BACK(CardKind.EVENT),
    CHANGE_PLACES(CardKind.EVENT),
    YOU_BETTER_RUN(CardKind.EVENT),
    SEDUCTION(CardKind.EVENT),
    SCARY(CardKind.EVENT),
    IM_COMFORTABLE(CardKind.EVENT),
    NO_THANKS(CardKind.EVENT),
    MISSED(CardKind.EVENT),
    NO_BARBECUE(CardKind.EVENT),
    QUARANTINE(CardKind.EVENT),
    BARRED_DOOR(CardKind.EVENT),
    EMPTY(CardKind.EVENT);

    private final CardKind cardKind;
}
