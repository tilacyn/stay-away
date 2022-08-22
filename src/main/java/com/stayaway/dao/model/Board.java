package com.stayaway.dao.model;

import com.stayaway.core.handler.*;
import com.stayaway.core.state.BoardState;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.cards.CardType;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "boards")
@Data
@Builder(toBuilder = true)
@CompoundIndex(name = "gameId_stage", def = "{'gameId' : 1, 'stage': 1}", background = true)
public class Board {

    @Id
    private final String id;

    private final String gameId;

    private int stage;

    private final int turn;

    @Field("players")
    private final Player currentPlayer;

    private final Direction direction;

    private final List<CardType> deck;

    private final List<CardType> trash;

    private BoardState boardState;

    @BsonIgnore
    private ExchangeHandler exchangeHandler;
    @BsonIgnore
    private DrawHandler drawHandler;
    @BsonIgnore
    private DiscardHandler discardHandler;
    @BsonIgnore
    private PlayHandler playHandler;
    @BsonIgnore
    private DefendHandler defendHandler;
    @BsonIgnore
    private ConfirmHandler confirmHandler;

    public Board copy() {
        return Board.builder()
                .id(RandomStringUtils.randomAlphabetic(10))
                .currentPlayer(currentPlayer)
                .trash(trash)
                .deck(deck)
                .direction(direction)
                .turn(turn)
                .stage(stage)
                .gameId(gameId)
                .build();
    }

    public void registerHandlers() {
        boardState.registerHandlers(this);
    }
}
