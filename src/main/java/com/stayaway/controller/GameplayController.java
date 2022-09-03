package com.stayaway.controller;

import com.stayaway.core.GameplayManager;
import com.stayaway.core.action.DiscardAction;
import com.stayaway.core.action.DrawAction;
import com.stayaway.core.action.PlayAction;
import com.stayaway.request.DefendRequest;
import com.stayaway.request.DiscardRequest;
import com.stayaway.request.ExchangeRequest;
import com.stayaway.request.PlayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class GameplayController {

    private final GameplayManager gameplayManager;
    private final Logger logger = LoggerFactory.getLogger(GameplayController.class);


    public GameplayController(GameplayManager gameplayManager) {
        this.gameplayManager = gameplayManager;
    }

    @PostMapping("/v1/game/{gameId}/play")
    public ResponseEntity<Void> play(@PathVariable String gameId, @RequestBody PlayRequest request, Principal principal) {
        logger.info("[PLAY] [{}] [{}]", principal.getName(), gameId);
        gameplayManager.play(new PlayAction(principal.getName(), request.getCardType(), request.getTarget()), gameId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameId}/discard")
    public ResponseEntity<Void> discard(@PathVariable String gameId, @RequestBody DiscardRequest request, Principal principal) {
        logger.info("[DISCARD] [{}] [{}]", principal.getName(), gameId);
        gameplayManager.discard(new DiscardAction(principal.getName(), request.getCardType()), gameId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameId}/draw")
    public ResponseEntity<Void> draw(@PathVariable String gameId, Principal principal) {
        logger.info("[DRAW] [{}] [{}]", principal.getName(), gameId);
        gameplayManager.draw(new DrawAction(principal.getName()), gameId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameId}/defend")
    public ResponseEntity<Void> defend(@PathVariable String gameId, @RequestBody DefendRequest request, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameId}/exchange")
    public ResponseEntity<Void> exchange(@PathVariable String gameId, @RequestBody ExchangeRequest request, Principal principal) {
        return ResponseEntity.ok().build();
    }


    @PostMapping("/v1/game/{gameId}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable String gameId, Principal principal) {
        return ResponseEntity.ok().build();
    }
}
