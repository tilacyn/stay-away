package com.stayaway.controller;

import com.stayaway.core.GameplayManager;
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

    @PostMapping("/v1/game/{gameID}/play")
    public ResponseEntity<Void> play(@PathVariable String gameID, @RequestBody PlayRequest request, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameID}/discard")
    public ResponseEntity<Void> discard(@PathVariable String gameID, @RequestBody DiscardRequest request, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameID}/draw")
    public ResponseEntity<Void> draw(@PathVariable String gameID, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameID}/defend")
    public ResponseEntity<Void> defend(@PathVariable String gameID, @RequestBody DefendRequest request, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameID}/exchange")
    public ResponseEntity<Void> exchange(@PathVariable String gameID, @RequestBody ExchangeRequest request, Principal principal) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/game/{gameID}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable String gameID, Principal principal) {
        return ResponseEntity.ok().build();
    }
}
