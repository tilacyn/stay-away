package com.stayaway;

import com.stayaway.request.DiscardRequest;
import org.junit.jupiter.api.Test;

import static com.stayaway.Constants.ALICE;
import static com.stayaway.Constants.BOB;
import static org.assertj.core.api.Assertions.assertThat;


public class DiscardGameplayTest extends IntegrationTestBase {

    @Test
    public void should_createDrawDiscardMvc() throws Exception {
        String login = ALICE;
        String gameId = Utils.createAndStart(testMvc, login, "game1");

        testMvc.performDraw(gameId, login);
        testMvc.performDiscard(gameId, login, new DiscardRequest(0));
        var boardAfter = testMvc.performGetBoard(gameId, login);
        assertThat(boardAfter.getCards()).size().isEqualTo(4);
        assertThat(boardAfter.getCurrentPlayerUserID()).isEqualTo(login);
    }

    @Test
    public void check_discardPermissions() throws Exception {
        String player1 = ALICE;
        String player2 = BOB;
        String gameId = Utils.createAndStart(testMvc, player1, "game1");

        testMvc.performDraw(gameId, player1);
        var discardRequest = new DiscardRequest(0);
        var invalidCardNumber = new DiscardRequest(7);

        testMvc.expectDiscardStatus(gameId, player2, discardRequest, 409);
        testMvc.expectDiscardStatus(gameId, player1, invalidCardNumber, 400);
        // todo uncomment when exchanging is implemented
        // testMvc.performDiscard(gameId, player1, discardRequest);
        // testMvc.expectDiscardNotAllowed(gameId, player1, discardRequest);
        // testMvc.expectDiscardNotAllowed(gameId, player1, notInHandCardDiscardRequest);
        // testMvc.expectDiscardNotAllowed(gameId, player2, discardRequest);
    }

}
