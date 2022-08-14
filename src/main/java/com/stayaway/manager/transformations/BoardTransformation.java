package com.stayaway.manager.transformations;

import com.stayaway.dao.model.Board;

public interface BoardTransformation {
    Board run(Board board);
}
