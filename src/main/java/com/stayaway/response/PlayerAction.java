package com.stayaway.response;

public enum PlayerAction {
    PENDING,
    CHOOSING_CARD_TO_PLAY_OR_DISCARD,
    CHOOSING_CARD_TO_EXCHANGE, //todo extend with target
    ANALYZING, //todo extend with cards and target
    CHOOSING_THE_PLAYER_TO_CHANGE_SEATS_WITH //todo extend with candidates
    //.. and so on
}
