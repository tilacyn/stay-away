package com.stayaway.service;

import com.stayaway.dao.GameDAO;
import com.stayaway.model.GameModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameDAO gameDAO;

    public GameService(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public List<GameModel> listGames() {
        return gameDAO.listGames();
    }

    public void createGame(String name, List<String> userIDs) {
        gameDAO.createGame(name, userIDs);
    }


}
