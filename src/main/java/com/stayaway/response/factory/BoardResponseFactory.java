package com.stayaway.response.factory;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.Card;
import com.stayaway.dao.model.User;
import com.stayaway.response.BoardResponse;
import com.stayaway.response.PlayerAction;
import com.stayaway.service.PlayerUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoardResponseFactory {

    private final PlayerResponseFactory playerResponseFactory;

    public BoardResponseFactory(PlayerResponseFactory playerResponseFactory) {
        this.playerResponseFactory = playerResponseFactory;
    }

    //todo implement
    public BoardResponse buildProto(Board board, List<User> boardPlayers, User user) {
        return new BoardResponse(
                boardPlayers.stream().map(playerResponseFactory::buildProto).collect(Collectors.toList()),
                getTheThingUserId(board, user),
                PlayerUtils.getCurrentPlayer(board).getUserId(),

                getCurrentPlayerAction(board, user),
                getCurrentCardToBePlaying(board),
                PlayerUtils.getPlayerCards(board, user),
                getGameName()
        );
    }

    //todo implement
    private PlayerAction getCurrentPlayerAction(Board board, User user) {
        return PlayerAction.PENDING;
    }

    //todo implement
    private Card getCurrentCardToBePlaying(Board board) {
        return Card.ANALYSIS;
    }

    //todo implement
    private String getGameName() {
        return "gameName";
    }

    private String getTheThingUserId(Board board, User user) {
        Board.Player player = PlayerUtils.getPlayerByUser(board, user);
        return player.canSeeTheThing() ? PlayerUtils.getTheThing(board).getUserId() : null;
    }
}
