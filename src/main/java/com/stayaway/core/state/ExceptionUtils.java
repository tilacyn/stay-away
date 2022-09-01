package com.stayaway.core.state;

import com.stayaway.exception.StayAwayException;
import com.stayaway.model.cards.CardType;

class ExceptionUtils {
    static StayAwayException playerActionNotExpected(String currentPlayerLogin, String actionLogin) {
        String messageTemplate = "action from player [%s] not expected, current player is [%s]";
        String message = String.format(messageTemplate, actionLogin, currentPlayerLogin);
        return StayAwayException.conflict(message);
    }

    static StayAwayException noSuchCardInHand(String currentPlayerLogin, CardType card) {
        String messageTemplate = "no such card [%s] in player's [%s] hand";
        String message = String.format(messageTemplate, card.toString(), currentPlayerLogin);
        return StayAwayException.conflict(message);
    }
}
