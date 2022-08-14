package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.Card;
import com.stayaway.dao.model.User;
import com.stayaway.exception.StayAwayException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

//todo consider to move these methods to PlayerService
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerUtils {
    public static Board.Player getPlayerByUser(Board board, User user) throws StayAwayException {
        return board.getPlayers().stream().filter(player -> player.getLogin().equals(user.getLogin())).findFirst()
                // todo implement spectators mode
                .orElseThrow(() -> StayAwayException.internalError("No such user[" + user.getLogin() + "] on the board[" + board.getId() + "]"));
    }

    public static Board.Player getTheThing(Board board) throws StayAwayException {
        return board.getPlayers().stream().filter(player -> Board.PlayerType.THE_THING.equals(player.getType())).findFirst()
                .orElseThrow(() -> StayAwayException.internalError("There is no The Thing on the board[" + board.getId() + "]"));
    }

    public static Board.Player getCurrentPlayer(Board board) throws StayAwayException {
        if (board.getPlayers().size() <= board.getCurrentPlayer()) {
            throw StayAwayException.internalError("Current player index more than amount of players on the board[" + board.getId() + "]");
        }
        return board.getPlayers().get(board.getCurrentPlayer());
    }

    public static List<Card> getPlayerCards(Board board, User user) throws StayAwayException {
        Board.Player player = getPlayerByUser(board, user);
        return player.getCards();
    }
}
