package com.stayaway.service;

import com.stayaway.dao.GameDAO;
import com.stayaway.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameDAO gameDAO;
    private final BoardService boardService;

    public GameService(GameDAO gameDAO, BoardService boardService) {
        this.gameDAO = gameDAO;
        this.boardService = boardService;
    }

    public List<Game> listGames() {
        return gameDAO.listGames();
    }

    public String createGame(String name, String ownerID) {
        return gameDAO.createGame(name, ownerID);
    }


    public Game getGame(String id) {
        return gameDAO.getGame(id);
    }

    public String startGame(String id) {
        var game = getGame(id);
//      each game should have a single host thus no need to provide multithreading guarantees
        var status = game.getStatus();
        if (status == Game.GameStatus.PREGAME) {
            var boardId = boardService.createBoard(game);
            var newGame = new Game(game.getId(), game.getName(), game.getOwnerID(), Game.GameStatus.RUNNING, game.getUserIDs());
            gameDAO.saveGame(newGame);
            return boardId;
        } else {
            throw new RuntimeException(String.format("game with id %s cannot be started, it is in status %s", id, status));
        }
    }

    public void enterPregame(String userID, String gameID) {
        var game = getGame(gameID);
        game.getUserIDs().add(userID);
    }

    // todo implement client-server interaction logic handling client disconnected events
    public void exitPregame(String userID, String gameID) {
//        todo
    }
}
