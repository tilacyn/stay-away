package com.stayaway;

import com.stayaway.dao.model.User;
import com.stayaway.request.CreateGameRequest;
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
public class DrawGameplayTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(Constants.MONGO_IMAGE);

    @Autowired
    public DrawGameplayTest(MockMvc mvc, UserService userService) {
        this.testMvc = new TestMvc(mvc);
        this.userService = userService;
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add(Constants.SPRING_DATA_MONGODB_URI_PROPERTY, mongoDBContainer::getReplicaSetUrl);
    }


    private final TestMvc testMvc;
    private final UserService userService;

    @Before
    public void saveUsers() {
        userService.save(new User("Alice"));
        userService.save(new User("Bob"));
        userService.save(new User("RomaElizarov"));
        userService.save(new User("Daniel"));
    }

    @Test
    public void should_createAndDrawMvc() throws Exception {
        String login = "Alice";

        var createGameRequest = new CreateGameRequest();
        createGameRequest.setName("game1");

        String gameId = testMvc.createAndStartGame(createGameRequest, login);
        testMvc.performDraw(gameId, login);
        var boardResponse = testMvc.performGetBoard(gameId, login);
        assertThat(boardResponse.getCards()).size().isEqualTo(5);
        assertThat(boardResponse.getCurrentPlayerUserID()).isEqualTo(login);
    }

    @Test
    public void check_drawPermissions() throws Exception {
        String player1 = "Alice";
        String player2 = "Bob";

        var createGameRequest = new CreateGameRequest();
        createGameRequest.setName("game1");

        String gameId = testMvc.createAndStartGame(createGameRequest, player1);
        testMvc.expectDrawNotAllowed(gameId, player2);
        testMvc.expectDrawNotAllowed(gameId, player2);
        testMvc.performDraw(gameId, player1);
        testMvc.expectDrawNotAllowed(gameId, player1);
        testMvc.expectDrawNotAllowed(gameId, player2);
    }


}
