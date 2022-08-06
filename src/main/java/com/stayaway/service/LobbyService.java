package com.stayaway.service;

import com.stayaway.dao.LobbyDAO;
import com.stayaway.model.GameModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LobbyService {
    private LobbyDAO lobbyDAO;

    public LobbyService(LobbyDAO lobbyDAO) {
        this.lobbyDAO = lobbyDAO;
    }

    public List<GameModel> listGames() {
        return lobbyDAO.listGames();
    }

    public void createGame(String name, List<String> userIDs) {
        lobbyDAO.createGame(name, userIDs);
    }
}
