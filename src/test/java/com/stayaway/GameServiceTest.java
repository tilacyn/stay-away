package com.stayaway;

import com.stayaway.core.state.DrawingBoardState;
import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.User;
import com.stayaway.model.board.state.BoardStatus;
import com.stayaway.request.CreateGameRequest;
import com.stayaway.response.BoardResponse;
import com.stayaway.service.GameService;
import com.stayaway.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Testcontainers
@AutoConfigureDataMongo
@AutoConfigureMockMvc
public class GameServiceTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @Autowired
    public GameServiceTest(MockMvc mvc, GameService gameService, UserService userService) {
        this.testMvc = new TestMvc(mvc);
        this.gameService = gameService;
        this.userService = userService;
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    private final TestMvc testMvc;
    private final GameService gameService;
    private final UserService userService;


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
