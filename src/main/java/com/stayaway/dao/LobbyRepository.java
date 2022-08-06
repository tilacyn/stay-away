package com.stayaway.dao;


import com.stayaway.model.GameModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LobbyRepository implements GameDAO {
    private final List<GameModel> gameModels = new ArrayList<>();

    {
        gameModels.add(new GameModel("id1", "name1", List.of(), GameModel.GameStatus.PREGAME));
        gameModels.add(new GameModel("id2", "name2", List.of(), GameModel.GameStatus.PREGAME));
    }

    @Override
    public void createGame(String name, List<String> userIDs) {
        gameModels.add(new GameModel("some-id", name, userIDs, GameModel.GameStatus.PREGAME));
    }

    @Override
    public List<GameModel> listGames() {
        return new ArrayList<>(gameModels);
    }
}
