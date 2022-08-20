package com.stayaway.core.state;

import com.stayaway.core.action.DefendAction;
import com.stayaway.core.action.PlayConfirmAction;
import com.stayaway.core.action.ViewCardsAction;
import com.stayaway.core.handler.DefendHandler;
import com.stayaway.core.handler.PlayConfirmHandler;
import com.stayaway.core.handler.ViewCardsHandler;
import com.stayaway.dao.model.Board;
import com.stayaway.model.board.state.BoardStatus;

// TODO
public class PlayingBoardState implements BoardState, DefendHandler, ViewCardsHandler, PlayConfirmHandler {
    private Board board;

    @Override
    public void defend(DefendAction action) {

    }

    @Override
    public void viewCards(ViewCardsAction action) {

    }

    @Override
    public void confirm(PlayConfirmAction action) {

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
        board.setPlayConfirmHandler(this);
        board.setDefendHandler(this);
        board.setViewCardsHandler(this);
        this.board = board;
    }

}
