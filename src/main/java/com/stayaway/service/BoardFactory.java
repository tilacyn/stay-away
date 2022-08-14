package com.stayaway.service;

import com.stayaway.dao.model.BoardState;
import com.stayaway.dao.model.Game;
import com.stayaway.manager.BasicCardsDistributor;
import com.stayaway.utils.PlayerUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

class BoardFactory {
    private final Logger logger = LoggerFactory.getLogger(BoardFactory.class);
    BoardState create(Game game) {
        var cardsDistributor = new BasicCardsDistributor(game.getUserLogins());
        cardsDistributor.generate();
        var theThingLogin = cardsDistributor.getTheThing();
        var players = cardsDistributor.getPlayersCards()
                .entrySet().stream()
                .map(entry -> new BoardState.Player(
                        entry.getKey(),
                        entry.getKey().equals(theThingLogin) ? BoardState.PlayerType.THE_THING : BoardState.PlayerType.HUMAN,
                        entry.getValue()))
                .collect(Collectors.toList());
        return BoardState.builder()
                .deck(cardsDistributor.getDeck())
                .trash(cardsDistributor.getTrash())
                .direction(BoardState.Direction.RIGHT)
                .currentPlayer(PlayerUtils.fromList(players, BoardState.Direction.RIGHT))
                .stage(0)
                .turn(0)
                .id(RandomStringUtils.randomAlphabetic(10))
                .gameId(game.getId())
                .build();
    }
}
