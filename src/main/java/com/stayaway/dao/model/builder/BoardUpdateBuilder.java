package com.stayaway.dao.model.builder;

import com.stayaway.core.state.BoardState;
import com.stayaway.dao.model.Board;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.cards.CardType;
import com.stayaway.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class BoardUpdateBuilder extends BoardBuilder {

    private int turn;

    private Player currentPlayer;

    private Direction direction;

    private List<CardType> deck;

    private List<CardType> trash;

    private BoardState boardState;

    public BoardUpdateBuilder(Board board, BoardState newState) {
        super(board.getGameId(), board.getStage() + 1);
        this.turn = board.getTurn();
        this.currentPlayer = board.getCurrentPlayer();
        this.direction = board.getDirection();
        this.deck = new ArrayList<>(board.getDeck());
        this.trash = new ArrayList<>(board.getTrash());
        this.boardState = newState;
    }

    public CardType getTopCard() {
        return deck.get(0);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public BoardUpdateBuilder removeTopCard() {
        deck.remove(0);
        return this;
    }

    public BoardUpdateBuilder addToHand(String login, CardType card) {
        Player player = PlayerUtils.getPlayerByLogin(currentPlayer, login);
        player.getCards().add(card);
        return this;
    }

    public Board build() {
        return new Board(id, gameId, stage, turn, currentPlayer, direction, deck, trash, boardState);
    }

    public BoardUpdateBuilder removeFromHand(String login, CardType card) {
        Player player = PlayerUtils.getPlayerByLogin(currentPlayer, login);
        player.getCards().remove(card);
        return this;
    }

    public BoardUpdateBuilder addToTrash(CardType card) {
        trash.add(card);
        return this;
    }
}
