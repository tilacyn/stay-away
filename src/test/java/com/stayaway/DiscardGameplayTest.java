package com.stayaway;

import com.stayaway.dao.model.User;
import com.stayaway.request.DiscardRequest;
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
public class DiscardGameplayTest {
    private static final List<String> testLogins = List.of(ALICE, BOB, ROMA_ELIZAROV, DANIEL);

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(Constants.MONGO_IMAGE);

    @Autowired
    public DiscardGameplayTest(MockMvc mvc, UserService userService) {
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
