package com.stayaway.core;

import com.stayaway.model.cards.CardType;

import java.util.List;
import java.util.Map;

public interface CardsDistributor {
    Map<String, List<CardType>> getPlayersCards();

    List<CardType> getDeck();

    List<CardType> getTrash();

    String getTheThing();
}
