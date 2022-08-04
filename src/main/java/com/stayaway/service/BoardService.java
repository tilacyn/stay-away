package com.stayaway.service;

import com.stayaway.dao.BoardDAO;
import com.stayaway.proto.BoardProto;
import com.stayaway.proto.BoardProtoFactory;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardDAO boardDAO;
    private final BoardProtoFactory boardProtoFactory;

    public BoardService(BoardDAO boardDAO, BoardProtoFactory boardProtoFactory) {
        this.boardDAO = boardDAO;
        this.boardProtoFactory = boardProtoFactory;
    }

    public BoardProto getBoardProto(String id) {
        return boardProtoFactory.create(boardDAO.getBoard(id));
    }

}
