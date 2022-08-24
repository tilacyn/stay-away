package com.stayaway.core.state;

import com.stayaway.core.action.ConfirmAction;
import com.stayaway.core.action.DefendAction;
import com.stayaway.core.handler.ConfirmHandler;
import com.stayaway.core.handler.DefendHandler;
import com.stayaway.dao.model.Board;
import com.stayaway.model.board.state.BoardStatus;

// TODO
public class PlayingBoardState implements BoardState, DefendHandler, ConfirmHandler {
    private Board board;

    @Override
    public void defend(DefendAction action) {

    }

    @Override
    public void confirm(ConfirmAction action) {

    }


    @Override
    public BoardStatus getStatus() {
        return BoardStatus.PLAYING_CARD;
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
        board.setDefendHandler(this);
        board.setConfirmHandler(this);
        this.board = board;
    }

}
