package com.stayaway.proto;

import com.stayaway.cards.Card;
import lombok.Data;

import java.util.List;

@Data
public class BoardProto {
    private List<String> players;
    private CardTypeProto topDeckCardType;
//    should be empty if user is not authorized to see which player the thing is
    private String theThingUserID;
    private String currentPlayerUserID;
    private MoveInProgressProto playCardStatus;
    List<Card> cards;
}
