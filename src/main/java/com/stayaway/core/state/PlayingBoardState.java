package com.stayaway.core.state;

import com.stayaway.core.action.ConfirmAction;
import com.stayaway.core.action.DefendAction;
import com.stayaway.core.handler.ConfirmHandler;
import com.stayaway.core.handler.DefendHandler;
import com.stayaway.model.board.state.BoardStatus;

// TODO
public class PlayingBoardState extends AbstractBoardState implements DefendHandler, ConfirmHandler {

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
    public void doRegisterHandlers() {
        board.setDefendHandler(this);
        board.setConfirmHandler(this);
    }

}
