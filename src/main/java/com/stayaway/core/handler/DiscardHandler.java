package com.stayaway.core.handler;

import com.stayaway.dao.model.Board;
import com.stayaway.core.action.DiscardAction;

public interface DiscardHandler {
    void discard(DiscardAction action);
}
