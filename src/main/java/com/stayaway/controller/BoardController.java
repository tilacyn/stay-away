package com.stayaway.controller;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.User;
import com.stayaway.exception.StayAwayException;
import com.stayaway.response.BoardResponse;
import com.stayaway.response.factory.BoardResponseFactory;
import com.stayaway.service.BoardService;
import com.stayaway.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class BoardController {

    private final BoardResponseFactory boardResponseFactory;

    private final BoardService boardService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    public BoardController(BoardResponseFactory boardResponseFactory, BoardService boardService, UserService userService) {
        this.boardResponseFactory = boardResponseFactory;
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping("/board/{boardId}")
    public String getBoardPage(@PathVariable String boardId, Model model, Principal principal) {
        logger.info("[GET_BOARD_PAGE] [{}]", principal.getName());
        return "boardPage";
    }

    @GetMapping("/v1/board/{gameId}")
    @ResponseBody
    public BoardResponse getBoard(@PathVariable String gameId, Principal principal) throws StayAwayException {
        String login = principal.getName();
        logger.info("[GET_BOARD] [{}] [{}]", login, gameId);
        Board board = boardService.getCurrentBoardState(gameId);
        //maybe need to introduce kind of BoardAnswer(which includes users) and move user service call to board service
        List<User> boardPlayers = userService.getBoardPlayers(board);
        User user = userService.getUser(login);

        return boardResponseFactory.buildReponse(board, boardPlayers, user);
    }
}
