package com.stayaway.proto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stayaway.cards.Card;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardProto {
    private List<String> players;
    private CardTypeProto topDeckCardType;
//    should be empty if user is not authorized to see which player the thing is
    private String theThingUserID;
    private String currentPlayerUserID;
    private MoveInProgressProto playCardStatus;
    private List<Card> cards;
}
