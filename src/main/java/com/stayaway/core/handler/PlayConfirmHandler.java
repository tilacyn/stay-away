package com.stayaway.core.handler;

import com.stayaway.core.action.PlayConfirmAction;

// for cases when user confirms that card will played on him
// instead of playing defense
public interface PlayConfirmHandler {
    void confirm(PlayConfirmAction action);
}
