package com.stayaway.model.board.player;

import java.util.Iterator;
import java.util.Objects;

public class PlayerIterator implements Iterator<Player> {

    private final Player start;
    private Player current;

    public PlayerIterator(Player start) {
        this.start = start;
    }

    @Override
    public boolean hasNext() {
        return !Objects.equals(current, start);
    }

    @Override
    public Player next() {
        Player res;
        if (current == null) {
            current = start.getLeft();
            res = start;
        } else {
            res = current;
            current = current.getLeft();
        }
        return res;
    }
}
