package com.stayaway.controller;

import com.stayaway.request.CreateGameRequest;
import com.stayaway.response.GameResponse;
import com.stayaway.response.GameResponseFactory;
import com.stayaway.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GameController {

    private final GameService gameService;
    private final GameResponseFactory gameProtoFactory;
    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController(GameService gameService, GameResponseFactory gameProtoFactory) {
        this.gameService = gameService;
        this.gameProtoFactory = gameProtoFactory;
    }


    @GetMapping("/lobby")
    public String getLobbyPage(Model model, Principal principal) {
        logger.info("[GET_LOBBY_PAGE] [{}]", principal.getName());
        var games = gameService.listGames();
        model.addAttribute("games", games);
        return "lobbyPage";
    }

    @GetMapping("/pregame/{gameID}")
    public String getPregamePage(@PathVariable String gameID, Model model, Principal principal) {
        var userID = principal.getName();
        logger.info("[GET_PREGAME_PAGE] [{}]", userID);
        var game = gameService.getGame(gameID);
        gameService.enterPregame(userID, gameID);
        model.addAttribute("users", new ArrayList<>(game.getUserIDs()));
        return "pregamePage";
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

    @PostMapping("/v1/start/{gameID}")
    public ResponseEntity<Void> startGame(@PathVariable String gameID, Principal principal) {
        logger.info("[START_GAME] [{}] [{}]", gameID, principal.getName());
        gameService.startGame(gameID);
        logger.info("[GAME_STARTED] [{}] [{}]", gameID, principal.getName());
        String location = String.format("/board/%s", gameID);
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

}
