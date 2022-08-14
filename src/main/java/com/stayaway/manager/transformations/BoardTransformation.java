package com.stayaway.manager.transformations;

import com.stayaway.dao.model.BoardState;

public interface BoardTransformation {
    BoardState run(BoardState boardState);
}
