package com.stayaway.core.state;

import com.stayaway.core.action.DrawAction;
import com.stayaway.core.handler.DrawHandler;
import com.stayaway.dao.model.builder.BoardUpdateBuilder;
import com.stayaway.model.board.state.BoardStatus;
import com.stayaway.model.cards.CardType;

public class DrawingBoardState extends AbstractBoardState implements DrawHandler {

    @Override
    public void draw(DrawAction action) {
        validate(action);
        builder = new BoardUpdateBuilder(board, new ChoosingCardBoardState());
        performDraw();
        preconditionsFulfilled = true;
    }

    private void validate(DrawAction action) {
        String currentPlayerLogin = board.getCurrentPlayer().getLogin();
        String actionLogin = action.getLogin();
        if (!currentPlayerLogin.equals(actionLogin)) {
            throw ExceptionUtils.playerActionNotExpected(actionLogin);
        }
    }

    private void performDraw() {
        CardType card = builder.getTopCard();
        builder.removeTopCard()
                .addToHand(builder.getCurrentPlayer().getLogin(), card);
    }

    @Override
    public BoardStatus getStatus() {
        return BoardStatus.DRAWING;
    }

    @Override
    public void doRegisterHandlers() {
        board.setDrawHandler(this);
    }

}
