package com.stayaway;

import com.stayaway.dao.model.User;
import com.stayaway.request.PlayRequest;
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

import static com.stayaway.Constants.ALICE;
import static com.stayaway.Constants.BOB;
import static com.stayaway.Constants.DANIEL;
import static com.stayaway.Constants.ROMA_ELIZAROV;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlayGameplayTest {
    private static final List<String> testLogins = List.of(ALICE, BOB, ROMA_ELIZAROV, DANIEL);

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(Constants.MONGO_IMAGE);

    @Autowired
    public PlayGameplayTest(MockMvc mvc, UserService userService) {
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
        testLogins.forEach(login -> userService.save(new User(login)));
    }

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
        var notEnoughCardsRequest = new PlayRequest(7, BOB);


        testMvc.expectPlayStatus(gameId, player1, wrongTargetRequest, 400);
        testMvc.expectPlayStatus(gameId, player1, notEnoughCardsRequest, 409);
        testMvc.expectPlayStatus(gameId, player2, playRequest, 409);
    }


}
