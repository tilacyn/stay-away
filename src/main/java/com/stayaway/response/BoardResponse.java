package com.stayaway.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stayaway.model.cards.CardType;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardResponse {
    private final List<PlayerResponse> players;
    //should be empty if user is not authorized to see which player the thing is
    @Nullable
    private final String theThingUserID;
    private final String currentPlayerUserID;
    private final PlayerAction playerAction;
    @Nullable
    private final CardType cardBeingPlayed;
    private final List<CardType> cards;
    // todo get if from game collection
    private final String name;
}
