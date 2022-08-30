package com.stayaway.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.stayaway.core.BasicCardsDistributor;
import com.stayaway.core.CardsDistributor;
import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.Game;
import com.stayaway.dao.model.builder.BoardCreateBuilder;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.board.player.PlayerType;
import com.stayaway.utils.PlayerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        return new BoardCreateBuilder(game.getId())
                .setDeck(cardsDistributor.getDeck())
                .setTrash(cardsDistributor.getTrash())
                .setCurrentPlayer(PlayerUtils.fromList(players, Direction.DEFAULT))
                .build();
    }
}
