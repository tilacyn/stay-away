package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.repository.BoardRepository;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    private final Logger boardLogger = LoggerFactory.getLogger(BoardService.class);

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board getCurrentBoardState(String gameID) {
        if (gameID == null) {
            throw StayAwayException.missingInput("gameID");
        }
        return boardRepository.findFirstByGameIDOrderByStageDesc(gameID).orElseThrow(() -> StayAwayException.notFound(gameID, StayAwayException.EntityType.BOARD));
    }

    public void save(Board board) {
        boardRepository.save(board);
    }

    /**
     * creates board for a specified game
     * @param game game associated with board
     */
    public void createBoard(Game game) {
        try {
            var board = new BoardFactory().create(game);
            save(board);
        } catch (IllegalArgumentException e) {
            throw StayAwayException.conflict(String.format("cannot create board for game (%s): ", game.getId()) + e.getMessage());
        }
    }

}
