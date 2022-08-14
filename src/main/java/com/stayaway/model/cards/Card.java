package com.stayaway.model.cards;

import com.stayaway.dao.model.BoardState;
import com.stayaway.dao.model.CardType;

public interface Card {

    BoardState applyAction(BoardState status);

    CardType getType();
}
