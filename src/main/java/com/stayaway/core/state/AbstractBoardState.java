package com.stayaway.core.state;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.builder.BoardUpdateBuilder;

public abstract class AbstractBoardState implements BoardState {
    protected Board board;
    protected BoardUpdateBuilder builder;
    protected boolean preconditionsFulfilled;

    @Override
    public boolean checkPreconditionsFulfilled() {
        return preconditionsFulfilled;
    }

    @Override
    public Board transform() {
        return builder.build();
    }

    @Override
    public void registerHandlers(Board board) {
        this.board = board;
        doRegisterHandlers();
    }

    protected abstract void doRegisterHandlers();
}
