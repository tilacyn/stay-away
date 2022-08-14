package com.stayaway.model.cards;

import com.stayaway.dao.model.BoardState;
import com.stayaway.dao.model.CardType;

public class EmptyCard implements Card {
    @Override
    public BoardState applyAction(BoardState status) {
        return status;
    }

    @Override
    public CardType getType() {
        return CardType.EMPTY;
    }
}
