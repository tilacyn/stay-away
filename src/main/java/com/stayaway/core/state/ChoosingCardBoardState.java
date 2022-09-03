package com.stayaway.core.state;

import com.stayaway.core.action.DiscardAction;
import com.stayaway.core.action.PlayAction;
import com.stayaway.core.handler.DiscardHandler;
import com.stayaway.core.handler.PlayHandler;
import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.builder.BoardUpdateBuilder;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.board.state.BoardStatus;

public class ChoosingCardBoardState implements BoardState, PlayHandler, DiscardHandler {
    private Board board;

    private DiscardAction discardAction;

    @Override
    public void discard(DiscardAction action) {
        validateDiscard(action);
        discardAction = action;
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

    @Override
    public boolean checkPreconditionsFulfilled() {
        return discardAction != null;
    }

    @Override
    public Board transform() {
        if (discardAction != null) {
            BoardUpdateBuilder boardBuilder = new BoardUpdateBuilder(board, new ExchangingBoardState());
            performDiscard(boardBuilder);
            return boardBuilder.build();
        }
        throw StayAwayException.internalError("play not yet supported");
    }

    private void performDiscard(BoardUpdateBuilder builder) {
        String login = board.getCurrentPlayer().getLogin();
        builder.removeFromHand(login, discardAction.getCard())
                .addToTrash(discardAction.getCard());
    }

    public void registerHandlers(Board board) {
        board.setPlayHandler(this);
        board.setDiscardHandler(this);
        this.board = board;
    }
}
