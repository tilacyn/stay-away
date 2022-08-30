package com.stayaway.dao.model.builder;

import java.util.List;

import com.stayaway.core.state.BoardState;
import com.stayaway.core.state.DrawingBoardState;
import com.stayaway.dao.model.Board;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.cards.CardType;

public class BoardCreateBuilder extends BoardBuilder {
    private int turn;

    private Player currentPlayer;

    private Direction direction;

    private List<CardType> deck;

    private List<CardType> trash;

    private BoardState boardState;


    public BoardCreateBuilder(String gameId) {
        super(gameId, 0);
        direction = Direction.DEFAULT;
        turn = 0;
        boardState = new DrawingBoardState();
    }

    public BoardCreateBuilder setTurn(int turn) {
        this.turn = turn;
        return this;
    }

    public BoardCreateBuilder setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        return this;
    }

    public BoardCreateBuilder setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public BoardCreateBuilder setDeck(List<CardType> deck) {
        this.deck = deck;
        return this;
    }

    public BoardCreateBuilder setTrash(List<CardType> trash) {
        this.trash = trash;
        return this;
    }

    public BoardCreateBuilder setBoardState(BoardState boardState) {
        this.boardState = boardState;
        return this;
    }

    public Board build() {
        return new Board(id, gameId, stage, turn, currentPlayer, direction, deck, trash, boardState);
    }
}
