package com.stayaway.controller;

import com.stayaway.core.GameplayManager;
import com.stayaway.core.action.factory.DrawActionFactory;
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
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameId}/discard")
    public ResponseEntity<Void> discard(@PathVariable String gameId, @RequestBody DiscardRequest request, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameId}/draw")
    public ResponseEntity<Void> draw(@PathVariable String gameId, Principal principal) {
        var action = new DrawActionFactory().create(principal.getName());
        gameplayManager.draw(action, gameId);
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

    @PostMapping("/v1/game/{gameId}/confirmPlay")
    public ResponseEntity<Void> confirmPlay(@PathVariable String gameId, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameId}/viewCards")
    public ResponseEntity<Void> viewCards(@PathVariable String gameId, Principal principal) {
        return ResponseEntity.ok().build();
    }
}