package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.repository.BoardRepository;
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

}
