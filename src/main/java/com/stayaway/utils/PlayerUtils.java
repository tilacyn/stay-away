package com.stayaway.utils;

import java.util.ArrayList;
import java.util.List;

import com.stayaway.dao.model.Board;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.board.player.PlayerType;
import com.stayaway.model.cards.CardType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

//todo consider to move these methods to PlayerService
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerUtils {

    //todo refactor
    public static Player getPlayerByUser(Board board, String user) throws StayAwayException {
        return getPlayersList(board).stream().filter(player -> player.getLogin().equals(user)).findFirst()
                // todo implement spectators mode
                .orElseThrow(() -> StayAwayException.internalError("No such user[" + user + "] on the board[" + board.getId() + "]"));
    }

    //todo refactor
    public static Player getTheThing(Board board) throws StayAwayException {
        return getPlayersList(board).stream().filter(player -> PlayerType.THE_THING.equals(player.getType())).findFirst()
                .orElseThrow(() -> StayAwayException.internalError("There is no The Thing on the board[" + board.getId() + "]"));
    }

    public static List<CardType> getPlayerCards(Board board, String user) throws StayAwayException {
        Player player = getPlayerByUser(board, user);
        return player.getCards();
    }

    public static List<Player> getPlayersList(Board board) {
        return getPlayersList(board.getCurrentPlayer(), board.getDirection());
    }

    public static List<Player> getPlayersList(Player player, Direction direction) {
        Player curr = player;
        List<Player> players = new ArrayList<>();
        while (!curr.getNext(direction).equals(player)) {
            players.add(curr);
            curr = curr.getNext(direction);
        }
        players.add(curr);
        return players;
    }

    public static Player fromList(List<Player> players, Direction direction) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setNext(direction, players.get((i + 1) % players.size()));
        }
        return players.get(0);
    }
}
