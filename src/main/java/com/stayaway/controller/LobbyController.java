package com.stayaway.controller;

import com.stayaway.input.CreateGameInput;
import com.stayaway.proto.GameProto;
import com.stayaway.proto.GameProtoFactory;
import com.stayaway.service.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LobbyController {

    private final LobbyService lobbyService;
    private final GameProtoFactory gameProtoFactory;
    private final Logger logger = LoggerFactory.getLogger(LobbyController.class);

    public LobbyController(LobbyService lobbyService, GameProtoFactory gameProtoFactory) {
        this.lobbyService = lobbyService;
        this.gameProtoFactory = gameProtoFactory;
    }


    @GetMapping("/lobby")
    public String getLobbyPage(Model model, Principal principal) {
        logger.info("[GET_LOBBY_PAGE] [{}]", principal.getName());
        var games = lobbyService.listGames();
        model.addAttribute("games", games);
        return "lobbyPage";
    }

    @PostMapping("/v1/create")
    public ResponseEntity<Void> createGame(@RequestBody CreateGameInput createGameInput, Principal principal) {
        logger.info("[CREATE_GAME] [{}]", principal.getName());
        try {
            lobbyService.createGame(createGameInput.getName(), createGameInput.getUserIDs());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/games")
    @ResponseBody
    public List<GameProto> listGames(Principal principal) {
        logger.info("[LIST_GAMES] [{}]", principal.getName());
        return lobbyService.listGames().stream()
                .map(gameProtoFactory::createGame)
                .collect(Collectors.toList());
    }




}
