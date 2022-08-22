package com.stayaway.service;

import com.stayaway.core.BasicCardsDistributor;
import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.Game;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.board.player.PlayerType;
import com.stayaway.utils.PlayerUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

class BoardFactory {
    private final Logger logger = LoggerFactory.getLogger(BoardFactory.class);

    Board create(Game game) {
        var cardsDistributor = new BasicCardsDistributor(game.getUserLogins());
        cardsDistributor.generate();
        var theThingLogin = cardsDistributor.getTheThing();
        var players = cardsDistributor.getPlayersCards()
                .entrySet().stream()
                .map(entry -> new Player(
                        entry.getKey(),
                        entry.getKey().equals(theThingLogin) ? PlayerType.THE_THING : PlayerType.HUMAN,
                        entry.getValue()))
                .collect(Collectors.toList());
        return Board.builder()
                .deck(cardsDistributor.getDeck())
                .trash(cardsDistributor.getTrash())
                .direction(Direction.RIGHT)
                .currentPlayer(PlayerUtils.fromList(players, Direction.RIGHT))
                .stage(0)
                .turn(0)
                .id(RandomStringUtils.randomAlphabetic(10))
                .gameId(game.getId())
                .build();
    }
}
