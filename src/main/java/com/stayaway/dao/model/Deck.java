package com.stayaway.dao.model;

import lombok.Data;

import java.util.List;

@Data
public class Deck {

    //todo custom deserializer into linked list
    private final List<Card> cards;
}
