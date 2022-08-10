package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.repository.BoardRepository;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.Game;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board getCurrentBoardState(String id) {
        if (id == null) {
            throw StayAwayException.missingInput("boardId");
        }
        return boardRepository.findFirstByIdOrderByStageDesc(id).orElseThrow(() -> StayAwayException.notFound(id, StayAwayException.EntityType.BOARD));
    }

    /**
     * creates board for a specified game
     * @param game game associated with board
     * @return board id
     */
    public String createBoard(Game game) {
//        TODO implement
        return "1";
    }
}
