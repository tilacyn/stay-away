package com.stayaway.dao.model;

import java.util.Set;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "games")
public class Game {
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
