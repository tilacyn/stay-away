package com.stayaway.controller;

import com.stayaway.proto.BoardProto;
import com.stayaway.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class BoardController {
    private final BoardService boardService;
    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board/{boardId}")
    public String getBoardPage(@PathVariable String boardId, Model model, Principal principal) {
        logger.info("[GET_BOARD_PAGE] [{}]", principal.getName());
        return "boardPage";
    }

//    TODO
    @GetMapping("/v1/board/{boardId}")
    @ResponseBody
    public BoardProto getBoard(@PathVariable String boardId, Principal principal) {
        String userID = principal.getName();
        logger.info("[GET_BOARD] [{}]", userID);
        return boardService.getBoardProto(boardId, userID);
    }
}
