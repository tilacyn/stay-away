package com.stayaway.core.state;

import com.stayaway.core.action.DiscardAction;
import com.stayaway.core.action.PlayAction;
import com.stayaway.core.handler.DiscardHandler;
import com.stayaway.core.handler.PlayHandler;
import com.stayaway.dao.model.Board;
import com.stayaway.model.board.state.BoardStatus;

// TODO
public class ChoosingCardBoardState implements BoardState, PlayHandler, DiscardHandler {
    private Board board;

    @Override
    public void discard(DiscardAction action) {

    }

    @Override
    public void play(PlayAction action) {

    }

    @Override
    public BoardStatus getStatus() {
        return BoardStatus.CHOOSING_CARD_TO_PLAY_OR_DISCARD;
    }

    @Override
    public boolean checkPreconditionsFulfilled() {
        return false;
    }

    @Override
    public Board transform() {
        return null;
    }

    public void registerHandlers(Board board) {
        board.setPlayHandler(this);
        board.setDiscardHandler(this);
        this.board = board;
    }
}
