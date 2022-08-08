package com.stayaway.dao;


import com.stayaway.model.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
public class GameRepository implements GameDAO {
    private final List<Game> gameModels = new ArrayList<>();
    private int nextGameID = 3;

    {
        gameModels.add(new Game("1", "name1", "tilacyn", Game.GameStatus.PREGAME, new HashSet<>()));
        gameModels.add(new Game("2", "name2", "tilacyn", Game.GameStatus.PREGAME, new HashSet<>()));
    }

    @Override
    public String createGame(String name, String ownerID) {
        var gameID = nextGameID++;
        gameModels.add(new Game(String.valueOf(gameID), name, ownerID, Game.GameStatus.PREGAME, new HashSet<>()));
        return String.valueOf(gameID);
    }

    @Override
    public List<Game> listGames() {
        return new ArrayList<>(gameModels);
    }

    @Override
    public Game getGame(String id) {
        return gameModels.stream().filter(model -> model.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("game with id not found"));
    }

    @Override
    public void saveGame(Game game) {
        try {
            var oldGame = getGame(game.getId());
            gameModels.remove(oldGame);
        } catch (Exception ignored) {
        }
        gameModels.add(game);
    }


}
