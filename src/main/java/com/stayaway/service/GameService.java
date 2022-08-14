package com.stayaway.service;

import com.stayaway.dao.repository.GameRepository;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.Game;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final BoardService boardService;

    private final Logger logger = LoggerFactory.getLogger(GameService.class);

    public GameService(GameRepository gameRepository, BoardService boardService) {
        this.gameRepository = gameRepository;
        this.boardService = boardService;
    }

    public List<Game> listGames() {
        return gameRepository.findAll();
    }

    public String createGame(String name, String ownerID) {
        var id = RandomStringUtils.randomAlphabetic(10);
        var game = new Game(id, name, ownerID, Game.GameStatus.PREGAME, Set.of("tilacyn", "Kowalski1337", "hey_boris"));
        gameRepository.save(game);
        return id;
    }


    public Game getGame(String id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> StayAwayException.notFound(id, StayAwayException.EntityType.GAME));
    }

    public void startGame(String id) {
        var game = getGame(id);

//      each game should have a single host thus no need to provide multithreading guarantees
        var status = game.getStatus();
        if (status == Game.GameStatus.PREGAME) {
            boardService.createBoard(game);
            var newGame = new Game(game.getId(), game.getName(), game.getOwnerID(), Game.GameStatus.RUNNING, game.getUserIDs());
            gameRepository.save(newGame);
        } else {
            throw StayAwayException.conflict(String.format("game with id %s cannot be started, it is in status %s", id, status));
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
