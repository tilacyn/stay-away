package com.stayaway;

import com.stayaway.model.cards.CardType;
import com.stayaway.request.CreateGameRequest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

class Utils {
    static String createAndStart(TestMvc mvc, String login, String name) throws Exception {
        var createGameRequest = new CreateGameRequest();
        createGameRequest.setName(name);

        return mvc.createAndStartGame(createGameRequest, login);
    }

    static CardType getCardNotInHand(List<CardType> hand) {
        return Arrays.stream(CardType.values()).filter(Predicate.not(hand::contains)).findFirst().get();
    }

}
