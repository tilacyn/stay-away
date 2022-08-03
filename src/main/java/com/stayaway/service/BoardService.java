package com.stayaway.service;

import com.stayaway.dao.BoardDAO;
import com.stayaway.model.BoardModel;
import com.stayaway.proto.BoardProto;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardDAO boardDAO;

    public BoardService(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    public BoardProto getBoardProto(String id) {
        return mapModelToProto(boardDAO.getBoard(id));
    }

    private BoardProto mapModelToProto(BoardModel model) {
        return null;
    }
}
