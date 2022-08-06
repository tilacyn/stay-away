package com.stayaway.proto;

import com.stayaway.model.GameModel;
import org.springframework.stereotype.Component;

@Component
public class GameProtoFactory {
    public GameProto createGame(GameModel game) {
        return new GameProto(game.getGameID(), game.getName(), game.getUserIDs());
    }
}
