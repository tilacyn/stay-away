package com.stayaway.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "boards")
@Data
@Builder
//TODO gameId_stage index
@CompoundIndex(name = "id_stage", def = "{'id' : 1, 'stage': 1}", background = true)
public class Board {
    @Id
    private final String id;

    private final String gameID;

    private final List<Player> players;

    //stage != turn (looks like turn ~= stage / 3)
    private final int stage;

    private final int turn;

    private final int currentPlayer;

    private final Deck deck;

    private final MoveInProgress currentMove;

    @Data
    public static class Player {
        private final String login;
        private final PlayerType type;
        private final List<Card> cards;

        @JsonIgnore
        public boolean canSeeTheThing() {
            return PlayerType.THE_THING.equals(type) ||
                    PlayerType.INFECTED.equals(type) ||
                    PlayerType.DEAD.equals(type);
        }
    }

    public enum PlayerType {
        THE_THING,
        INFECTED,
        HUMAN,
        DEAD
    }

    public String toString() {
        ObjectMapper om = new ObjectMapper();
        String res;
        try {
            res = om.writeValueAsString(this);
        } catch (Exception e) {
            return String.format("failed to write json %s", e);
        }
        return res;
    }
}
