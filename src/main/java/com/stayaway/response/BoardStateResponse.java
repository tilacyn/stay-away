package com.stayaway.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stayaway.dao.model.CardType;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardStateResponse {
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
