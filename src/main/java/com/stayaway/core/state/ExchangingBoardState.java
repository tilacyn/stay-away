package com.stayaway.core.state;

import com.stayaway.core.action.DefendAction;
import com.stayaway.core.action.ExchangeAction;
import com.stayaway.core.handler.DefendHandler;
import com.stayaway.core.handler.ExchangeHandler;
import com.stayaway.dao.model.Board;
import com.stayaway.model.board.state.BoardStatus;

public class ExchangingBoardState implements BoardState, ExchangeHandler, DefendHandler {
    private Board board;

    @Override
    public void defend(DefendAction action) {
    }

    @Override
    public void exchange(ExchangeAction action) {
    }

    @Override
    public BoardStatus getStatus() {
        return BoardStatus.EXCHANGING;
    }

    @Override
    public boolean checkPreconditionsFulfilled() {
        return false;
    }

    @Override
    public Board transform() {
        return null;
    }

    @Override
    public void registerHandlers(Board board) {
        board.setExchangeHandler(this);
        board.setDefendHandler(this);
        this.board = board;
    }

}
