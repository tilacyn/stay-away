package com.stayaway.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stayaway.model.cards.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private List<PlayerResponse> players;
    //should be empty if user is not authorized to see which player the thing is
    @Nullable
    private String theThingUserID;
    private String currentPlayerUserID;
    private PlayerAction playerAction;
    @Nullable
    private CardType cardBeingPlayed;
    private List<CardType> cards;
    // todo get if from game collection
    private String name;
}
