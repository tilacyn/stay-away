package com.stayaway.core.state;

import com.stayaway.exception.StayAwayException;

class ExceptionUtils {
    static StayAwayException playerActionNotExpected(String currentPlayerLogin, String actionLogin) {
        String messageTemplate = "action from player [%s] not expected, current player is [%s]";
        String message = String.format(messageTemplate, actionLogin, currentPlayerLogin);
        return StayAwayException.conflict(message);
    }
}
