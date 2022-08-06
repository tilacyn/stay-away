package com.stayaway.proto;

import com.stayaway.dao.model.Card;
import lombok.Data;

import java.util.List;

@Data
public class PlayerProto {
    private String userID;
    private List<Card> cards;
}
