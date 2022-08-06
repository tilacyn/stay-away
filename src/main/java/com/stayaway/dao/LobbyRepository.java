package com.stayaway.dao;


import com.stayaway.model.GameModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LobbyRepository implements LobbyDAO {
    private final List<GameModel> gameModels = new ArrayList<>();

    @Override
    public void createGame(String name, List<String> userIDs) {
        gameModels.add(new GameModel("some-id", name, userIDs));
    }

    @Override
    public List<GameModel> listGames() {
        return new ArrayList<>(gameModels);
    }
}
