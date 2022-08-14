package com.stayaway.manager.transformations;

import com.stayaway.dao.model.Board;
import com.stayaway.manager.actions.UserAction;

public class ExchangingTransformation implements BoardTransformation {
    public ExchangingTransformation(UserAction action, Board state) {
    }

    @Override
    public Board run(Board board) {
        return board;
    }
}
