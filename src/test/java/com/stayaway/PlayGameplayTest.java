package com.stayaway;

import com.stayaway.request.PlayRequest;
import org.junit.jupiter.api.Test;

import static com.stayaway.Constants.ALICE;
import static com.stayaway.Constants.BOB;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayGameplayTest extends IntegrationTestBase {

    @Test
    public void should_createDrawPlayMvc() throws Exception {
        String login = ALICE;
        String gameId = Utils.createAndStart(testMvc, login, "game1");

        testMvc.performDraw(gameId, login);
        testMvc.expectPlayStatus(gameId, login, new PlayRequest(0, BOB), 200);
        var boardAfter = testMvc.performGetBoard(gameId, login);
        assertThat(boardAfter.getCards()).size().isEqualTo(4);
    }

    @Test
    public void check_playPermissions() throws Exception {
        String player1 = ALICE;
        String player2 = BOB;
        String gameId = Utils.createAndStart(testMvc, player1, "game1");

        testMvc.performDraw(gameId, player1);
        var playRequest = new PlayRequest(0, BOB);
        var wrongTargetRequest = new PlayRequest(0, "wrong-target");
        var invalidCardNumberRequest = new PlayRequest(7, BOB);


        testMvc.expectPlayStatus(gameId, player1, wrongTargetRequest, 400);
        testMvc.expectPlayStatus(gameId, player1, invalidCardNumberRequest, 400);
        testMvc.expectPlayStatus(gameId, player2, playRequest, 409);
    }


}
