package com.tilacyn.stayaway.dao;

import com.tilacyn.stayaway.board.Board;
import com.tilacyn.stayaway.model.BoardModel;

public interface BoardDAO {
    BoardModel getBoard(String id);
}
