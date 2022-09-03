package com.stayaway.core.state;

import com.stayaway.exception.StayAwayException;

class ExceptionUtils {
    static StayAwayException playerActionNotExpected(String currentPlayerLogin, String actionLogin) {
        String messageTemplate = "action from player [%s] not expected, current player is [%s]";
        String message = String.format(messageTemplate, actionLogin, currentPlayerLogin);
        return StayAwayException.conflict(message);
    }

    static StayAwayException notEnoughCardsInHand(int actionCardNumber, int handSize) {
        String messageTemplate = "there are only %d cards in hand, cannot retrieve card by number %d";
        String message = String.format(messageTemplate, handSize, actionCardNumber);
        return StayAwayException.conflict(message);
    }
}
