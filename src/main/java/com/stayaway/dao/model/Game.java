package com.stayaway.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "games")
public class Game {
    //    gameID should be equal to board id I guess
    @Id
    private final String id;
    private final String name;
    private final String ownerLogin;
    private final GameStatus status;
    private final Set<String> userLogins;

    public enum GameStatus {
        PREGAME, RUNNING, FINISHED
    }

}
