package com.stayaway.utils;

import com.stayaway.dao.model.BoardState;
import com.stayaway.dao.model.CardType;
import com.stayaway.exception.StayAwayException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//todo consider to move these methods to PlayerService
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerUtils {

    //todo refactor
    public static BoardState.Player getPlayerByUser(BoardState boardState, String user) throws StayAwayException {
        return getPlayersList(boardState).stream().filter(player -> player.getLogin().equals(user)).findFirst()
                // todo implement spectators mode
                .orElseThrow(() -> StayAwayException.internalError("No such user[" + user + "] on the board[" + boardState.getId() + "]"));
    }

    //todo refactor
    public static BoardState.Player getTheThing(BoardState boardState) throws StayAwayException {
        return getPlayersList(boardState).stream().filter(player -> BoardState.PlayerType.THE_THING.equals(player.getType())).findFirst()
                .orElseThrow(() -> StayAwayException.internalError("There is no The Thing on the board[" + boardState.getId() + "]"));
    }

    public static List<CardType> getPlayerCards(BoardState boardState, String user) throws StayAwayException {
        BoardState.Player player = getPlayerByUser(boardState, user);
        return player.getCards();
    }

    public static List<BoardState.Player> getPlayersList(BoardState boardState) {
        return getPlayersList(boardState.getCurrentPlayer(), boardState.getDirection());
    }

    public static List<BoardState.Player> getPlayersList(BoardState.Player player, BoardState.Direction direction) {
        BoardState.Player curr = player;
        List<BoardState.Player> players = new ArrayList<>();
        while (!curr.getNext(direction).equals(player)) {
            players.add(curr);
            curr = curr.getNext(direction);
        }
        players.add(curr);
        return players;
    }

    public static BoardState.Player fromList(List<BoardState.Player> players, BoardState.Direction direction) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setNext(direction, players.get((i + 1) % players.size()));
        }
        return players.get(0);
    }
}
