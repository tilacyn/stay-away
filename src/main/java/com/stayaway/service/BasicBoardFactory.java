package com.stayaway.service;

import com.stayaway.core.BasicCardsDistributor;
import com.stayaway.core.CardsDistributor;
import com.stayaway.core.state.DrawingBoardState;
import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.Game;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.board.player.PlayerType;
import com.stayaway.utils.PlayerUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BasicBoardFactory implements BoardFactory {
    private final Logger logger = LoggerFactory.getLogger(BasicBoardFactory.class);

    protected Set<String> getGameUserLogins(Game game) {
        return game.getUserLogins();
    }

    protected List<Player> createPlayers(CardsDistributor cardsDistributor) {
        return cardsDistributor.getPlayersCards()
                .entrySet().stream()
                .map(entry -> new Player(
                        entry.getKey(),
                        entry.getKey().equals(cardsDistributor.getTheThing()) ? PlayerType.THE_THING : PlayerType.HUMAN,
                        entry.getValue()))
                .collect(Collectors.toList());
    }

    public Board create(Game game) {
        var userLogins = getGameUserLogins(game);
        var cardsDistributor = new BasicCardsDistributor(userLogins);
        cardsDistributor.generate();
        var players = createPlayers(cardsDistributor);
        return Board.builder()
                .deck(cardsDistributor.getDeck())
                .trash(cardsDistributor.getTrash())
                .direction(Direction.RIGHT)
                .currentPlayer(PlayerUtils.fromList(players, Direction.RIGHT))
                .stage(0)
                .turn(0)
                .id(RandomStringUtils.randomAlphabetic(10))
                .gameId(game.getId())
                .boardState(new DrawingBoardState())
                .build();
    }
}
