package com.stayaway.controller;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.stayaway.dao.model.Board;
import com.stayaway.exception.StayAwayException;
import com.stayaway.request.CreateGameRequest;
import com.stayaway.request.DiscardRequest;
import com.stayaway.request.ExchangeRequest;
import com.stayaway.request.PlayRequest;
import com.stayaway.response.BoardResponse;
import com.stayaway.response.GameResponse;
import com.stayaway.response.GameResponseFactory;
import com.stayaway.response.factory.BoardResponseFactory;
import com.stayaway.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {

    private final GameService gameService;
    private final GameResponseFactory gameProtoFactory;
    private final BoardResponseFactory boardResponseFactory;
    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController(GameService gameService, GameResponseFactory gameProtoFactory,
                          BoardResponseFactory boardResponseFactory) {
        this.gameService = gameService;
        this.gameProtoFactory = gameProtoFactory;
        this.boardResponseFactory = boardResponseFactory;
    }


    @GetMapping("/lobby")
    public String getLobbyPage(Model model, Principal principal) {
        logger.info("[GET_LOBBY_PAGE] [{}]", principal.getName());
        var games = gameService.listGames();
        model.addAttribute("games", games);
        return "lobbyPage";
    }

    @GetMapping("/pregame/{gameId}")
    public String getPregamePage(@PathVariable String gameId, Model model, Principal principal) {
        var userID = principal.getName();
        logger.info("[GET_PREGAME_PAGE] [{}]", userID);
        var game = gameService.getGame(gameId);
        gameService.enterPregame(userID, gameId);
        model.addAttribute("users", new ArrayList<>(game.getUserLogins()));
        return "pregamePage";
    }

    @GetMapping("/game/{gameId}")
    public String getBoardPage(@PathVariable String gameId, Model model, Principal principal) {
        logger.info("[GET_BOARD_PAGE] [{}]", principal.getName());
        return "boardPage";
    }

    @PostMapping("/v1/create")
    public ResponseEntity<Void> createGame(@RequestBody CreateGameRequest createGameRequest, Principal principal) {
        String userID = principal.getName();
        logger.info("[CREATE_GAME] [{}]", userID);
        String gameID;
        gameID = gameService.createGame(createGameRequest.getName(), userID);
        String location = String.format("/pregame/%s", gameID);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @GetMapping("/v1/games")
    @ResponseBody
    public List<GameResponse> listGames(Principal principal) {
        logger.info("[LIST_GAMES] [{}]", principal.getName());
        return gameService.listGames().stream()
                .map(gameProtoFactory::buildGameResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/v1/game/{gameID}/start")
    public ResponseEntity<Void> startGame(@PathVariable String gameID, Principal principal) {
        logger.info("[START_GAME] [{}] [{}]", gameID, principal.getName());
        gameService.startGame(gameID);
        logger.info("[GAME_STARTED] [{}] [{}]", gameID, principal.getName());
        String location = String.format("/game/%s", gameID);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PostMapping("/v1/game/{gameID}/draw")
    public ResponseEntity<Void> draw(@PathVariable String gameID, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameID}/play")
    public ResponseEntity<Void> play(@PathVariable String gameID,
                                     @RequestBody PlayRequest request,
                                     Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameID}/discard")
    public ResponseEntity<Void> discard(@PathVariable String gameID,
                                        @RequestBody DiscardRequest request,
                                        Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameID}/exchange")
    public ResponseEntity<Void> exchange(@PathVariable String gameID,
                                         @RequestBody ExchangeRequest request,
                                         Principal principal) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/game/{gameId}/board")
    @ResponseBody
    public BoardResponse getBoard(@PathVariable String gameId, Principal principal) throws StayAwayException {
        String login = principal.getName();
        logger.info("[GET_BOARD] [{}] [{}]", login, gameId);
        Board board = gameService.getCurrentBoard(gameId);

        return boardResponseFactory.buildResponse(board, login);
    }

}
