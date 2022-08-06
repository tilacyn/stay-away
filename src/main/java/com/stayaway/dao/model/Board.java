package com.stayaway.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "boards")
@Data
public class Board {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private List<Player> players;

    private int currentPlayer;

    private Deck deck;

    @Data
    public static class Player {
        private String userID;
        private PlayerType playerType;
        private List<Card> cards;
    }

    public enum PlayerType {
        THE_THING,
        INFECTED,
        HUMAN
    }
}
