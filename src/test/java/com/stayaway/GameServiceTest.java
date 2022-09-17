package com.stayaway;

import com.stayaway.core.state.DrawingBoardState;
import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.User;
import com.stayaway.model.board.state.BoardStatus;
import com.stayaway.request.CreateGameRequest;
import com.stayaway.response.BoardResponse;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class GameServiceTest extends IntegrationTestBase {

    @Before
    public void saveUsers() {
        userService.save(new User("Alice"));
        userService.save(new User("Bob"));
        userService.save(new User("RomaElizarov"));
        userService.save(new User("Daniel"));
    }

    @Test
    public void should_createAndGetCurrentBoard() {
        String gameId = gameService.createGame("game1", "Alice");
        gameService.startGame(gameId);
        Board board = gameService.getCurrentBoard(gameId);

        assertThat(board.getGameId()).isEqualTo(gameId);
        assertThat(board.getTurn()).isEqualTo(0);
        assertThat(board.getStage()).isEqualTo(0);
        assertThat(board.getBoardState()).isInstanceOf(DrawingBoardState.class);
        assertThat(board.getBoardState().getStatus()).isEqualTo(BoardStatus.DRAWING);
        assertThat(board.getTrash()).isEmpty();
    }


    @Test
    public void should_createAndGetCurrentBoardMvc() throws Exception {
        String login = "Alice";

        var createGameRequest = new CreateGameRequest();
        createGameRequest.setName("game1");

        String gameId = testMvc.createAndStartGame(createGameRequest, login);

        BoardResponse boardResponse = testMvc.performGetBoard(gameId, login);

        assertThat(boardResponse.getPlayers()).isNotEmpty();
        assertThat(boardResponse.getCards()).size().isEqualTo(4);
        assertThat(boardResponse.getCurrentPlayerUserID()).isNotNull();
        assertThat(boardResponse.getName()).isNotNull();
        // todo other assertions when BoardResponse will be modified
    }

}
