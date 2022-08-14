package com.stayaway.service;

import java.util.List;
import java.util.Set;

import com.stayaway.dao.model.BoardState;
import com.stayaway.dao.model.Game;
import com.stayaway.dao.repository.BoardRepository;
import com.stayaway.dao.repository.GameRepository;
import com.stayaway.exception.StayAwayException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final BoardRepository boardRepository;

    private final Logger logger = LoggerFactory.getLogger(GameService.class);

    public GameService(GameRepository gameRepository, BoardRepository boardRepository) {
        this.gameRepository = gameRepository;
        this.boardRepository = boardRepository;
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
            //todo transaction
            createBoardState(game);
            var newGame = new Game(game.getId(), game.getName(), game.getOwnerLogin(), Game.GameStatus.RUNNING,
                    game.getUserLogins());
            gameRepository.save(newGame);
        } else {
            throw StayAwayException.conflict(String.format("game with id %s cannot be started, it is in status %s",
                    id, status));
        }
    }

    public void enterPregame(String userID, String gameID) {
        var game = getGame(gameID);
        game.getUserLogins().add(userID);
    }

    // todo implement client-server interaction logic handling client disconnected events
    public void exitPregame(String userID, String gameID) {
//        todo
    }

    public BoardState getCurrentBoardState(String gameID) {
        if (gameID == null) {
            throw StayAwayException.missingInput("gameID");
        }
        return boardRepository.findFirstByGameIdOrderByStageDesc(gameID).orElseThrow(() -> StayAwayException.notFound(gameID, StayAwayException.EntityType.BOARD));
    }

    public void saveBoardState(BoardState boardState) {
        boardRepository.save(boardState);
    }

    /**
     * creates board state for a specified game
     *
     * @param game game associated with board
     */
    private void createBoardState(Game game) {
        var board = new BoardFactory().create(game);
        saveBoardState(board);
    }
}
