package com.stayaway.core.state;

import com.stayaway.dao.model.Board;
import com.stayaway.model.board.state.BoardStatus;

public interface BoardState {

    BoardStatus getStatus();

    boolean checkPreconditionsFulfilled();

    Board transform();


    void registerHandlers(Board board);
}
