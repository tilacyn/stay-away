package com.stayaway.controller;

import com.stayaway.proto.BoardProto;
import com.stayaway.proto.BoardProtoFactory;
import com.stayaway.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    private final BoardProtoFactory boardProtoFactory;

    private final BoardService boardService;

    public BoardController(BoardProtoFactory boardProtoFactory, BoardService boardService) {
        this.boardProtoFactory = boardProtoFactory;
        this.boardService = boardService;
    }

    @GetMapping("/board/{boardId}")
    public String getBoardPage(@PathVariable String boardId, Model model) {
        return "boardPage";
    }

    //    TODO
    @GetMapping("/v1/board/{boardId}")
    @ResponseBody
    public BoardProto getBoard(@PathVariable String boardId) {
        return boardProtoFactory.buildProto(boardService.getBoard(boardId));
    }
}
