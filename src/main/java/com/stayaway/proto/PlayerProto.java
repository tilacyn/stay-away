package com.stayaway.proto;

import com.stayaway.cards.Card;
import lombok.Data;

import java.util.List;

@Data
public class PlayerProto {
    private String userID;
    private List<Card> cards;
}