package com.stayaway.model.board.state;

import java.util.function.Function;

import com.stayaway.dao.model.Board;
import com.stayaway.model.actions.CardChosenAction;
import com.stayaway.model.actions.ConfirmationAction;
import com.stayaway.model.actions.TargetChosenAction;

/**
 * @author Vadim Baydyuk <vbaydyuk@yandex-team.ru>
 * @since 14.08.2022
 */
public class DrawingCardBoardState implements BoardState {
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
        return BoardStatus.DRAWING;
    }
}
