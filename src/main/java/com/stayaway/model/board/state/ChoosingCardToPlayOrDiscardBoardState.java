package com.stayaway.model.board.state;

import java.util.function.Function;

import com.stayaway.dao.model.Board;
import com.stayaway.model.actions.CardChosenAction;
import com.stayaway.model.actions.ConfirmationAction;
import com.stayaway.model.actions.TargetChosenAction;

public class ChoosingCardToPlayOrDiscardBoardState implements BoardState {
    @Override
    public void applyCardChosenAction(CardChosenAction action) {

    }

    @Override
    public void applyTargetChosenAction(TargetChosenAction action) {

    }

    @Override
    public void applyConfirmationAction(ConfirmationAction action) {

    }

    @Override
    public boolean checkPreconditionsFulfilled() {
        return false;
    }

    @Override
    public Function<Board, Board> getTransition() {
        return null;
    }

    @Override
    public BoardStatus getStatus() {
        return BoardStatus.CHOOSING_CARD_TO_PLAY_OR_DISCARD;
    }
}
