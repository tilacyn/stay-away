package com.stayaway.dao;

import com.stayaway.model.Game;

import java.util.List;

public interface GameDAO {
    String createGame(String name, String ownerID);

    List<Game> listGames();

    Game getGame(String id);

    void saveGame(Game game);
}
