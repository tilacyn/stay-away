package com.stayaway.proto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stayaway.dao.model.Card;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardProto {
    private final List<String> players;
    private final CardTypeProto topDeckCardType;
//    should be empty if user is not authorized to see which player the thing is
    private final String theThingUserID;
    private final String currentPlayerUserID;
    private final MoveInProgressProto playCardStatus;
    private final List<Card> cards;
    private final String name;
}
