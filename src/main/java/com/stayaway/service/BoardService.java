package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.repository.BoardRepository;
import com.stayaway.model.Game;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board getBoard(String id) {
        return boardRepository.findById(id).orElseThrow(IllegalStateException::new);
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
