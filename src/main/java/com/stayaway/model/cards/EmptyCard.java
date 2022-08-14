package com.stayaway.model.cards;

import java.util.function.Function;

import com.stayaway.dao.model.Board;
import com.stayaway.model.actions.CardChosenAction;
import com.stayaway.model.actions.ConfirmationAction;
import com.stayaway.model.actions.TargetChosenAction;

public class EmptyCard implements Card {

    @Override
    public void applyCardChosenAction(CardChosenAction action) {
        //todo
    }

    @Override
    public void applyTargetChosenAction(TargetChosenAction action) {
        //todo
    }

    @Override
    public void applyConfirmationAction(ConfirmationAction action) {
        //todo
    }

    @Override
    public boolean checkPreconditionsFulfilled() {
        //todo
        return false;
    }

    @Override
    public Function<Board, Board> getTransition() {
        //todo
        return null;
    }

    @Override
    public CardType getType() {
        return CardType.EMPTY;
    }
}
