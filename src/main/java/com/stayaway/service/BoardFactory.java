package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.Card;
import com.stayaway.model.Game;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class BoardFactory {
    private final Logger logger = LoggerFactory.getLogger(BoardFactory.class);
    Board create(Game game) {
        var cardsDistributor = new BasicCardsDistributor(game.getUserIDs());
        cardsDistributor.generate();
        Map<String, List<Card>> playersCards = cardsDistributor.getPlayersCards();
        var theThingLogin = cardsDistributor.getTheThing();
        var players = cardsDistributor.getPlayersCards()
                .entrySet().stream()
                .map(entry -> new Board.Player(
                        entry.getKey(),
                        entry.getKey().equals(theThingLogin) ? Board.PlayerType.THE_THING : Board.PlayerType.HUMAN,
                        entry.getValue()))
                .collect(Collectors.toList());
        return Board.builder()
                .deck(cardsDistributor.getDeck())
                .players(players)
                .stage(0)
                .turn(0)
                .currentPlayer(RandomUtils.nextInt(0, players.size()))
                .id(RandomStringUtils.randomAlphabetic(10))
                .gameID(game.getId())
                .build();
    }
}
