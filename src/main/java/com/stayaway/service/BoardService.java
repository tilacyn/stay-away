package com.tilacyn.stayaway.service;

import com.tilacyn.stayaway.board.Board;
import com.tilacyn.stayaway.dao.BoardDAO;
import com.tilacyn.stayaway.model.BoardModel;
import com.tilacyn.stayaway.proto.BoardProto;
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
