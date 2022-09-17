package com.stayaway.core.state;

import com.stayaway.exception.StayAwayException;

class ExceptionUtils {
    static StayAwayException playerActionNotExpected(String player) {
        String messageTemplate = "action from player [%s] not expected";
        String message = String.format(messageTemplate, player);
        return StayAwayException.conflict(message);
    }

    static StayAwayException invalidCardNumber(int actionCardNumber, int handSize) {
        String messageTemplate = "there are only %d cards in hand, cannot retrieve card by number %d";
        String message = String.format(messageTemplate, handSize, actionCardNumber);
        return StayAwayException.badRequest(message);
    }

    static StayAwayException attemptToChoseCardToChangeTwice(String player, int chosen, int chosenPrev) {
        String messageTemplate =
                "attempt to to chose %d's card to exchange when %d's card was chosen previously by [%s]";
        String message = String.format(messageTemplate, chosen, chosenPrev, player);
        return StayAwayException.badRequest(message);
    }
}
