package com.stayaway.manager.transformations;

import com.stayaway.dao.model.BoardState;
import com.stayaway.manager.actions.UserAction;

public class ChoosingCardTransformation implements BoardTransformation {
    public ChoosingCardTransformation(UserAction action, BoardState state) {
    }

    @Override
    public BoardState run(BoardState boardState) {
        return boardState;
    }
}
