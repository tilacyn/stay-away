package com.stayaway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Game {
//    gameID should be equal to board id I guess
    private final String id;
    private final String name;
    private final String ownerID;
    private GameStatus status;
    private final Set<String> userIDs;

    public enum GameStatus {
        PREGAME, RUNNING, FINISHED
    }

}
