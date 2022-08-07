package com.stayaway.controller;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.User;
import com.stayaway.exception.StayAwayException;
import com.stayaway.proto.BoardResponse;
import com.stayaway.proto.builder.BoardResponseBuilder;
import com.stayaway.service.BoardService;
import com.stayaway.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class BoardController {

    private final BoardResponseBuilder boardResponseBuilder;

    private final BoardService boardService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    public BoardController(BoardResponseBuilder boardResponseBuilder, BoardService boardService, UserService userService) {
        this.boardResponseBuilder = boardResponseBuilder;
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping("/board/{boardId}")
    public String getBoardPage(@PathVariable String boardId, Model model, Principal principal) {
        logger.info("[GET_BOARD_PAGE] [{}]", principal.getName());
        return "boardPage";
    }

    //    TODO
    @GetMapping("/v1/board/{boardId}")
    @ResponseBody
    public BoardResponse getBoard(@PathVariable String boardId, Principal principal) throws StayAwayException {
        String userId = principal.getName();
        logger.info("[GET_BOARD] [{}]", userId);
        Board board = boardService.getCurrentBoardState(boardId);
        //maybe need to introduce kind of BoardAnswer(which includes users) and move user service call to board service
        List<User> boardPlayers = userService.getBoardPlayers(board);
        User user = userService.getUser(userId);

        return boardResponseBuilder.buildProto(board, boardPlayers, user);
    }
}
