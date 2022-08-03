package com.tilacyn.stayaway.proto;

import lombok.Data;

public enum MoveStageProto {
    CHOOSING_CARD_TO_PLAY,
    CHOOSING_CARD_TO_EXCHANGE_FIRST,
    CHOOSING_CARD_TO_EXCHANGE_SECOND,
    CHOOSING_CARD_TO_EXCHANGE_BOTH,
    WAITING_FOR_PLAY_RESPONSE,
    SHOWING_CARDS,
//    think about all cases in panic
}
