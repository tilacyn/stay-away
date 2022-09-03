package com.stayaway.core.state;

import com.stayaway.core.action.DiscardAction;
import com.stayaway.core.action.PlayAction;
import com.stayaway.core.handler.DiscardHandler;
import com.stayaway.core.handler.PlayHandler;
import com.stayaway.dao.model.builder.BoardUpdateBuilder;
import com.stayaway.model.board.state.BoardStatus;

public class ChoosingCardBoardState extends AbstractBoardState implements PlayHandler, DiscardHandler {

    @Override
    public void discard(DiscardAction action) {
        validateDiscard(action);
        builder = new BoardUpdateBuilder(board, new ExchangingBoardState());
        performDiscard(action);
        preconditionsFulfilled = true;
    }

    private void validateDiscard(DiscardAction action) {
        var currentPlayer = board.getCurrentPlayer();
        String actionLogin = action.getLogin();
        if (!currentPlayer.getLogin().equals(actionLogin)) {
            throw ExceptionUtils.playerActionNotExpected(currentPlayer.getLogin(), actionLogin);
        }
        if (!board.getCurrentPlayer().getCards().contains(action.getCard())) {
            throw ExceptionUtils.noSuchCardInHand(currentPlayer.getLogin(), action.getCard());
        }
    }

    @Override
    public void play(PlayAction action) {

    }

    @Override
    public BoardStatus getStatus() {
        return BoardStatus.CHOOSING_CARD_TO_PLAY_OR_DISCARD;
    }


    private void performDiscard(DiscardAction discardAction) {
        String login = board.getCurrentPlayer().getLogin();
        builder.removeFromHand(login, discardAction.getCard())
                .addToTrash(discardAction.getCard());
    }

    protected void doRegisterHandlers() {
        board.setPlayHandler(this);
        board.setDiscardHandler(this);
    }
}
