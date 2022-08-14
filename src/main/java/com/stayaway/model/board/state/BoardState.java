package com.stayaway.model.board.state;

import java.util.function.Function;

import com.stayaway.dao.model.Board;
import com.stayaway.model.actions.CardChosenAction;
import com.stayaway.model.actions.ConfirmationAction;
import com.stayaway.model.actions.TargetChosenAction;

public interface BoardState {

    // draw card action ??

    void applyCardChosenAction(CardChosenAction action);

    void applyTargetChosenAction(TargetChosenAction action);

    void applyConfirmationAction(ConfirmationAction action);

    boolean checkPreconditionsFulfilled();

    Function<Board, Board> getTransition();

    BoardStatus getStatus();
}
