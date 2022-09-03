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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.stayaway.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DrawGameplayTest {
    private static final List<String> testLogins = List.of(ALICE, BOB, ROMA_ELIZAROV, DANIEL);

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

    @Before
    public void saveUsers() {
        testLogins.forEach(login -> userService.save(new User(login)));
    }

    private final TestMvc testMvc;
    private final UserService userService;

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
