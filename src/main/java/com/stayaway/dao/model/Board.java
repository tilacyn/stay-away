package com.stayaway.dao.model;

import java.util.List;

import com.stayaway.core.handler.ConfirmHandler;
import com.stayaway.core.handler.DefendHandler;
import com.stayaway.core.handler.DiscardHandler;
import com.stayaway.core.handler.DrawHandler;
import com.stayaway.core.handler.ExchangeHandler;
import com.stayaway.core.handler.PlayHandler;
import com.stayaway.core.state.BoardState;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.cards.CardType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "boards")
@Data
@CompoundIndex(name = "gameId_stage", def = "{'gameId' : 1, 'stage': 1}", background = true)
public class Board {

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

    private final BoardState boardState;

    @Transient
    private ExchangeHandler exchangeHandler;
    @Transient
    private DrawHandler drawHandler;
    @Transient
    private DiscardHandler discardHandler;
    @Transient
    private PlayHandler playHandler;
    @Transient
    private DefendHandler defendHandler;
    @Transient
    private ConfirmHandler confirmHandler;

    public void registerHandlers() {
        boardState.registerHandlers(this);
    }

    public Player nextPlayer() {
        return currentPlayer.getNext(direction);
    }
}
