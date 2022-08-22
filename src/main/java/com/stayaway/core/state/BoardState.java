package com.stayaway.core.state;

import com.stayaway.dao.model.Board;

public interface BoardState {

    boolean checkPreconditionsFulfilled();

    Board transform();
}
