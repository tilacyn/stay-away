package com.stayaway.response.factory;

import com.stayaway.dao.model.BoardState;
import com.stayaway.dao.model.CardType;
import com.stayaway.response.BoardStateResponse;
import com.stayaway.response.PlayerAction;
import com.stayaway.utils.PlayerUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BoardStateResponseFactory {

    private final PlayerResponseFactory playerResponseFactory;

    public BoardStateResponseFactory(PlayerResponseFactory playerResponseFactory) {
        this.playerResponseFactory = playerResponseFactory;
    }

    //todo implement
    public BoardStateResponse buildResponse(BoardState boardState, String user) {
        return new BoardStateResponse(
                PlayerUtils.getPlayersList(boardState).stream()
                        .map(BoardState.Player::getLogin)
                        .map(playerResponseFactory::buildResponse)
                        .collect(Collectors.toList()),
                getTheThingUserId(boardState, user),
                boardState.getCurrentPlayer().getLogin(),

                getCurrentPlayerAction(boardState, user),
                getCurrentCardToBePlaying(boardState),
                PlayerUtils.getPlayerCards(boardState, user),
                getGameName()
        );
    }

    //todo implement
    private PlayerAction getCurrentPlayerAction(BoardState boardState, String user) {
        return PlayerAction.PENDING;
    }

    //todo implement
    private CardType getCurrentCardToBePlaying(BoardState boardState) {
        return CardType.ANALYSIS;
    }

    //todo implement
    private String getGameName() {
        return "gameName";
    }

    private String getTheThingUserId(BoardState boardState, String user) {
        BoardState.Player player = PlayerUtils.getPlayerByUser(boardState, user);
        return player.canSeeTheThing() ? PlayerUtils.getTheThing(boardState).getLogin() : null;
    }
}
