package com.stayaway.response.factory;

import java.util.stream.Collectors;

import com.stayaway.dao.model.Board;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.cards.CardType;
import com.stayaway.response.BoardResponse;
import com.stayaway.response.PlayerAction;
import com.stayaway.utils.PlayerUtils;
import org.springframework.stereotype.Component;

@Component
public class BoardResponseFactory {

    private final PlayerResponseFactory playerResponseFactory;

    public BoardResponseFactory(PlayerResponseFactory playerResponseFactory) {
        this.playerResponseFactory = playerResponseFactory;
    }

    //todo implement
    public BoardResponse buildResponse(Board board, String user) {
        return new BoardResponse(
                PlayerUtils.getPlayersList(board).stream()
                        .map(Player::getLogin)
                        .map(playerResponseFactory::buildResponse)
                        .collect(Collectors.toList()),
                getTheThingUserId(board, user),
                board.getCurrentPlayer().getLogin(),

                getCurrentPlayerAction(board, user),
                getCurrentCardToBePlaying(board),
                PlayerUtils.getPlayerCards(board, user),
                getGameName()
        );
    }

    //todo implement
    private PlayerAction getCurrentPlayerAction(Board board, String user) {
        return PlayerAction.PENDING;
    }

    //todo implement
    private CardType getCurrentCardToBePlaying(Board board) {
        return CardType.ANALYSIS;
    }

    //todo implement
    private String getGameName() {
        return "gameName";
    }

    private String getTheThingUserId(Board board, String user) {
        Player player = PlayerUtils.getPlayerByUser(board, user);
        return player.canSeeTheThing() ? PlayerUtils.getTheThing(board).getLogin() : null;
    }
}
