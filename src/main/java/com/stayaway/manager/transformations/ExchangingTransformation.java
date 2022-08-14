package com.stayaway.manager.transformations;

import com.stayaway.dao.model.BoardState;
import com.stayaway.manager.actions.UserAction;

public class ExchangingTransformation implements BoardTransformation {
    public ExchangingTransformation(UserAction action, BoardState state) {
    }

    @Override
    public BoardState run(BoardState boardState) {
        return boardState;
    }
}
