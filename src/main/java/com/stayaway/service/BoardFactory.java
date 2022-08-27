package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.Game;

public interface BoardFactory {
    Board create(Game game);
}
