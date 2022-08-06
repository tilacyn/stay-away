package com.stayaway.dao;

import com.stayaway.model.GameModel;

import java.util.List;

public interface GameDAO {
    void createGame(String name, List<String> userIDs);

    List<GameModel> listGames();
}
