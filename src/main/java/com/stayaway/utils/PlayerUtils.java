package com.stayaway.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.stayaway.dao.model.Board;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.board.player.PlayerType;
import com.stayaway.model.cards.CardType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerUtils {

    public static Player getPlayerByLogin(Board board, String login) {
        return getPlayerByLogin(board.getCurrentPlayer(), login);
    }

    public static Player getPlayerByLogin(Player player, String login) {
        return find(player, p -> p.getLogin().equals(login))
                .orElseThrow(() -> StayAwayException.internalError("No such user[" + login + "] on the board"));
    }

    public static Player getTheThing(Board board) throws StayAwayException {
        return find(board.getCurrentPlayer(), player -> PlayerType.THE_THING.equals(player.getType()))
                .orElseThrow(() -> StayAwayException.internalError("There is no The Thing on the board[" + board.getId() + "]"));
    }

    public static List<CardType> getPlayerCards(Board board, String user) throws StayAwayException {
        Player player = getPlayerByLogin(board, user);
        return player.getCards();
    }

    public static List<Player> getPlayersList(Board board) {
        return getPlayersList(board.getCurrentPlayer(), board.getDirection());
    }

    public static List<Player> getPlayersList(Player player, Direction direction) {
        List<Player> players = new ArrayList<>();
        player.forEach(players::add);
        if (!Direction.INIT.equals(direction)) {
            Collections.reverse(players);
        }
        return players;
    }

    public static Optional<Player> find(Player player, Predicate<Player> predicate) {
        for (Player current : player) {
            if (predicate.test(current)) {
                return Optional.of(current);
            }
        }
        return Optional.empty();
    }

    public static Player fromList(List<Player> players, Direction direction) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setNext(direction, players.get((i + 1) % players.size()));
        }
        return players.get(0);
    }
}
