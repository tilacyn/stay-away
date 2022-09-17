package com.stayaway;

import org.junit.jupiter.api.Test;

import static com.stayaway.Constants.ALICE;
import static com.stayaway.Constants.BOB;
import static org.assertj.core.api.Assertions.assertThat;


public class DrawGameplayTest extends IntegrationTestBase {

    @Test
    public void should_createAndDrawMvc() throws Exception {
        String login = ALICE;
        String gameId = Utils.createAndStart(testMvc, login, "game1");

        testMvc.performDraw(gameId, login);
        var boardResponse = testMvc.performGetBoard(gameId, login);
        assertThat(boardResponse.getCards()).size().isEqualTo(5);
        assertThat(boardResponse.getCurrentPlayerUserID()).isEqualTo(login);
    }

    @Test
    public void check_drawPermissions() throws Exception {
        String player1 = ALICE;
        String player2 = BOB;
        String gameId = Utils.createAndStart(testMvc, player1, "game1");

        testMvc.expectDrawNotAllowed(gameId, player2);
        testMvc.expectDrawNotAllowed(gameId, player2);
        testMvc.performDraw(gameId, player1);
        testMvc.expectDrawNotAllowed(gameId, player1);
        testMvc.expectDrawNotAllowed(gameId, player2);
    }


}
