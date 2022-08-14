package com.stayaway.dao.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "board_states")
@Data
@Builder
@CompoundIndex(name = "gameId_stage", def = "{'gameId' : 1, 'stage': 1}", background = true)
public class BoardState {
    @Id
    private final String id;

    private final String gameId;

    private final int stage;

    private final int turn;

    @Field("players")
    private final Player currentPlayer;

    private final Direction direction;

    private final List<CardType> deck;

    private final List<CardType> trash;

    private final BoardStatus boardStatus;


    @Data
    public static class Player {
        private final String login;
        private final PlayerType type;
        private final List<CardType> cards;
        @JsonIgnore
        private Player left;
        @JsonIgnore
        private Player right;

        @JsonIgnore
        public boolean canSeeTheThing() {
            return PlayerType.THE_THING.equals(type) ||
                    PlayerType.INFECTED.equals(type) ||
                    PlayerType.DEAD.equals(type);
        }

        @JsonIgnore
        public Player getNext(Direction direction) {
            if (Direction.LEFT.equals(direction)) {
                return left;
            } else {
                return right;
            }
        }

        @JsonIgnore
        public void setNext(Direction direction, Player player) {
            if (Direction.LEFT.equals(direction)) {
                left = player;
                player.right = this;
            } else {
                right = player;
                player.left = this;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Player player = (Player) o;
            return Objects.equals(login, player.login);
        }

        @Override
        public int hashCode() {
            return Objects.hash(login);
        }
    }

    public enum Direction {
        LEFT,
        RIGHT
    }

    public enum PlayerType {
        THE_THING,
        INFECTED,
        HUMAN,
        DEAD
    }

}
