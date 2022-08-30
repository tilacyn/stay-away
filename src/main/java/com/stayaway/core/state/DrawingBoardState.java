package com.stayaway.core.state;

import com.stayaway.core.action.DrawAction;
import com.stayaway.core.handler.DrawHandler;
import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.builder.BoardUpdateBuilder;
import com.stayaway.model.board.state.BoardStatus;
import com.stayaway.model.cards.CardType;

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
        BoardUpdateBuilder boardBuilder = new BoardUpdateBuilder(board, new ChoosingCardBoardState());
        performDraw(boardBuilder);
        return boardBuilder.build();
    }

    private void performDraw(BoardUpdateBuilder newBoard) {
        CardType card = newBoard.getTopCard();
        newBoard.removeTopCard()
                .addToHand(newBoard.getCurrentPlayer().getLogin(), card);
    }

    @Override
    public void registerHandlers(Board board) {
        board.setDrawHandler(this);
        this.board = board;
    }

}
