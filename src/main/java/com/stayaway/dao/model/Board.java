package com.stayaway.dao.model;

import com.stayaway.core.handler.*;
import com.stayaway.core.state.BoardState;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.cards.CardType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

    private final int stage;

    private final int turn;

    @Field("players")
    private final Player currentPlayer;

    private final Direction direction;

    private final List<CardType> deck;

    private final List<CardType> trash;

    private final BoardState boardState;

    @Setter
    @Getter
    private ExchangeHandler exchangeHandler;
    @Setter
    @Getter
    private DrawHandler drawHandler;
    @Setter
    @Getter
    private DiscardHandler discardHandler;
    @Setter
    @Getter
    private PlayHandler playHandler;
    @Setter
    @Getter
    private DefendHandler defendHandler;
    @Setter
    @Getter
    private ViewCardsHandler viewCardsHandler;
    @Setter
    @Getter
    private PlayConfirmHandler playConfirmHandler;
}
