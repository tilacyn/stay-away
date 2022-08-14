package com.stayaway.response;

import com.stayaway.dao.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameResponseFactory {
    public GameResponse buildGameResponse(Game game) {
        return new GameResponse(game.getId(), game.getName(), game.getUserLogins(), game.getStatus());
    }
}
