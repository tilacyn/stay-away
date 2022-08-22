package com.stayaway.core.state;

import com.stayaway.core.action.DefendAction;
import com.stayaway.core.action.PlayConfirmAction;
import com.stayaway.core.action.ConfirmAction;
import com.stayaway.core.handler.DefendHandler;
import com.stayaway.core.handler.PlayConfirmHandler;
import com.stayaway.core.handler.ConfirmHandler;
import com.stayaway.dao.model.Board;

public class PlayingBoardState implements BoardState, DefendHandler, ConfirmHandler, PlayConfirmHandler {
    private Board board;

    @Override
    public void defend(DefendAction action) {

    }

    @Override
    public void confirm(ConfirmAction action) {

    }

    @Override
    public void confirm(PlayConfirmAction action) {

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
        board.setPlayConfirmHandler(this);
        board.setDefendHandler(this);
        board.setViewCardsHandler(this);
    }
}
