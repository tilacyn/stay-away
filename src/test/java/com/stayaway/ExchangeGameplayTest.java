package com.stayaway;

import java.util.ArrayList;
import java.util.List;

import com.stayaway.model.cards.CardType;
import com.stayaway.request.DiscardRequest;
import com.stayaway.request.ExchangeRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.stayaway.Constants.ALICE;
import static com.stayaway.Constants.BOB;
import static com.stayaway.Constants.ROMA_ELIZAROV;

public class ExchangeGameplayTest extends IntegrationTestBase {
    @Test
    public void testExchange() throws Exception {
        String first = ALICE;
        String second = BOB;
        String gameId = Utils.createAndStart(testMvc, first, "game1");


        testMvc.performDraw(gameId, first);
        testMvc.performDiscard(gameId, first, new DiscardRequest(0));

        //todo I'm bleeding from my eyes, I will rewrite this bullshit, meaningless tests later STAYAWAY-42

        int firstCardNum = 1;
        List<CardType> firstCards = getPlayerCars(first, gameId);
        CardType firstCard = firstCards.get(firstCardNum);

        int secondCardNum = 2;
        List<CardType> secondCards = getPlayerCars(second, gameId);
        CardType secondCard = secondCards.get(secondCardNum);

        List<CardType> expectedFirstCards = new ArrayList<>(firstCards);
        expectedFirstCards.remove(firstCardNum);
        expectedFirstCards.add(secondCard);

        List<CardType> expectedSecondCards = new ArrayList<>(secondCards);
        expectedSecondCards.remove(secondCardNum);
        expectedSecondCards.add(firstCard);

        testMvc.expectExchangeStatus(gameId, first, new ExchangeRequest(firstCardNum), 200);
        testMvc.expectExchangeStatus(gameId, second, new ExchangeRequest(secondCardNum), 200);

        List<CardType> actualFirstCards = getPlayerCars(first, gameId);
        List<CardType> actualSecondCards = getPlayerCars(second, gameId);
        Assertions.assertEquals(expectedFirstCards, actualFirstCards);
        Assertions.assertEquals(expectedSecondCards, actualSecondCards);
    }

    @Test
    public void testUnexpectedPlayer() throws Exception {
        String gameId = Utils.createAndStart(testMvc, ALICE, "game1");


        testMvc.performDraw(gameId, ALICE);
        testMvc.performDiscard(gameId, ALICE, new DiscardRequest(0));

        testMvc.expectExchangeStatus(gameId, ROMA_ELIZAROV, new ExchangeRequest(0), 409);
    }

    @Test
    public void testWrongState() throws Exception {
        String gameId = Utils.createAndStart(testMvc, ALICE, "game1");
        testMvc.expectExchangeStatus(gameId, ALICE, new ExchangeRequest(0), 409);
    }

    @Test
    public void testInvalidCardNum() throws Exception {
        String gameId = Utils.createAndStart(testMvc, ALICE, "game1");


        testMvc.performDraw(gameId, ALICE);
        testMvc.performDiscard(gameId, ALICE, new DiscardRequest(0));

        testMvc.expectExchangeStatus(gameId, ALICE, new ExchangeRequest(-1), 400);
        testMvc.expectExchangeStatus(gameId, ALICE, new ExchangeRequest(10), 400);
    }

    private List<CardType> getPlayerCars(String player, String gameId) throws Exception {
        return testMvc.performGetBoard(gameId, player).getCards();
    }
}
