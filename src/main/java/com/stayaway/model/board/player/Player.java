package com.stayaway.model.board.player;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.board.Direction;
import com.stayaway.model.cards.CardType;
import lombok.Data;

@Data
public class Player implements Iterable<Player> {
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

    @JsonIgnore
    public Player getByLogin(String login) {
        if (Objects.equals(this.login, login)) {
            return this;
        }
        Player tmp = this.left;
        while (tmp != this) {
            if (Objects.equals(tmp.login, login)) {
                return tmp;
            }
            tmp = tmp.left;
        }
        throw StayAwayException.notFound(login, StayAwayException.EntityType.PLAYER);
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

    @Override
    public Iterator<Player> iterator() {
        return new PlayerIterator(this);
    }
}
