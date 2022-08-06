package com.stayaway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameModel {
//    gameID is the same thing as board id ig
    private String id;
    private String name;
    private List<String> userIDs;
    private GameStatus gameStatus;

    public enum GameStatus {
        PREGAME, RUNNING, FINISHED
    }
}
