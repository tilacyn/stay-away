package com.stayaway.core.state;

import com.stayaway.core.action.DiscardAction;
import com.stayaway.core.action.PlayAction;
import com.stayaway.core.handler.DiscardHandler;
import com.stayaway.core.handler.PlayHandler;
import com.stayaway.dao.model.builder.BoardUpdateBuilder;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.board.state.BoardStatus;
import com.stayaway.model.cards.CardType;
import com.stayaway.utils.PlayerUtils;

public class ChoosingCardBoardState extends AbstractBoardState implements PlayHandler, DiscardHandler {

    @Override
    public void discard(DiscardAction action) {
        validateDiscard(action);
        builder = new BoardUpdateBuilder(board, new ExchangingBoardState());
        performDiscard(action);
        preconditionsFulfilled = true;
    }

    private void validateDiscard(DiscardAction action) {
        validateActionLogin(action.getLogin());
        validateCardInHand(action.getCard());
    }

    @Override
    public void play(PlayAction action) {
        validatePlay(action);
        builder = new BoardUpdateBuilder(board, new ExchangingBoardState());
        performPlay(action);
        preconditionsFulfilled = true;
    }

    private void validatePlay(PlayAction action) {
        validateActionLogin(action.getLogin());
        validateCardInHand(action.getCard());
        validateTarget(action.getTarget());
    }


    private void validateActionLogin(String actionLogin) {
        var currentPlayer = board.getCurrentPlayer();
        if (!currentPlayer.getLogin().equals(actionLogin)) {
            throw ExceptionUtils.playerActionNotExpected(currentPlayer.getLogin(), actionLogin);
        }
    }

    private void validateCardInHand(CardType card) {
        var currentPlayer = board.getCurrentPlayer();
        if (!currentPlayer.getCards().contains(card)) {
            throw ExceptionUtils.noSuchCardInHand(currentPlayer.getLogin(), card);
        }
    }

    private void validateTarget(String target) {
        try {
            PlayerUtils.getPlayerByLogin(board, target);
        } catch (StayAwayException e) {
            throw StayAwayException.badRequest(String.format("target player [%s] not found", target));
        }
    }

    private void performDiscard(DiscardAction discardAction) {
        String login = board.getCurrentPlayer().getLogin();
        builder.removeFromHand(login, discardAction.getCard())
                .addToTrash(discardAction.getCard());
    }

    private void performPlay(PlayAction action) {
        String login = board.getCurrentPlayer().getLogin();
        builder.removeFromHand(login, action.getCard())
                .addToTrash(action.getCard());
    }


    @Override
    public BoardStatus getStatus() {
        return BoardStatus.CHOOSING_CARD_TO_PLAY_OR_DISCARD;
    }

    protected void doRegisterHandlers() {
        board.setPlayHandler(this);
        board.setDiscardHandler(this);
    }
}
