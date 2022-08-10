package com.stayaway.response;

import com.stayaway.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameResponseFactory {
    public GameResponse buildGameResponse(Game game) {
        return new GameResponse(game.getId(), game.getName(), game.getUserIDs(), game.getStatus());
    }
}
