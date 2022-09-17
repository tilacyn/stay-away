package com.stayaway.core.state;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.builder.BoardUpdateBuilder;
import com.stayaway.utils.PlayerUtils;
import org.springframework.data.annotation.Transient;

//todo STAYAWAY-41
public abstract class AbstractBoardState implements BoardState {
    @Transient
    protected Board board;
    @Transient
    protected BoardUpdateBuilder builder;
    @Transient
    protected boolean preconditionsFulfilled;

    @Override
    public boolean checkPreconditionsFulfilled() {
        return preconditionsFulfilled;
    }

    @Override
    public Board transform() {
        doTransform();
        return builder.build();
    }

    protected void doTransform() {
        //do nothing
    }

    @Override
    public void registerHandlers(Board board) {
        this.board = board;
        doRegisterHandlers();
    }

    protected abstract void doRegisterHandlers();

    protected void validateCardNumber(String login, int cardNumber) {
        var currentPlayer = PlayerUtils.getPlayerByLogin(board, login);
        int handSize = currentPlayer.getCards().size();
        if (handSize <= cardNumber || cardNumber < 0) {
            throw ExceptionUtils.invalidCardNumber(cardNumber, handSize);
        }
    }
}
