package com.stayaway.proto;

import lombok.Data;

import java.util.List;

@Data
public class BoardProto {
    private List<PlayerProto> players;
    private CardTypeProto topDeckCardType;
//    should be empty if user is not authorized to see which player the thing is
    private String theThingUserID;
    private String currentPlayerUserID;
    private MoveInProgressProto playCardStatus;
}
