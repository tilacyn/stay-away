package com.stayaway.core.state;

import com.stayaway.core.action.DrawAction;
import com.stayaway.core.handler.DrawHandler;
import com.stayaway.dao.model.Board;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.board.state.BoardStatus;

public class DrawingBoardState implements BoardState, DrawHandler {
    private Board board;
    private boolean drawRequested;

    @Override
    public void draw(DrawAction action) {
        String currentPlayerLogin = board.getCurrentPlayer().getLogin();
        String actionLogin = action.getLogin();
        if (!currentPlayerLogin.equals(actionLogin)) {
            throw ExceptionUtils.playerActionNotExpected(currentPlayerLogin, actionLogin);
        }
        drawRequested = true;
    }

    @Override
    public BoardStatus getStatus() {
        return BoardStatus.DRAWING;
    }

    @Override
    public boolean checkPreconditionsFulfilled() {
        return drawRequested;
    }

    @Override
    public Board transform() {
        var newBoard = board.copy();
        incrementStage(newBoard);
        performDraw(newBoard);
        var newState = new ChoosingCardBoardState();
        newBoard.setBoardState(newState);
        return newBoard;
    }

    private void performDraw(Board newBoard) {
        var deck = newBoard.getDeck();
        var deckTopCard = deck.remove(0);
        newBoard.getCurrentPlayer().getCards().add(0, deckTopCard);
    }

    private void incrementStage(Board newBoard) {
        newBoard.setStage(board.getStage() + 1);
    }

    @Override
    public void registerHandlers(Board board) {
        board.setDrawHandler(this);
        this.board = board;
    }

}
