package com.stayaway.core.state;

import com.stayaway.core.action.DrawAction;
import com.stayaway.core.handler.DrawHandler;
import com.stayaway.dao.model.Board;

public class DrawingBoardState implements BoardState, DrawHandler {
    private Board board;

    @Override
    public void draw(DrawAction action) {
    }

    @Override
    public boolean checkPreconditionsFulfilled() {
        return false;
    }

    @Override
    public Board transform() {
        return null;
    }

    public void registerHandlers() {
        board.setDrawHandler(this);
    }

}
