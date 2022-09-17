package com.stayaway;

import java.util.List;

import com.stayaway.dao.model.User;
import com.stayaway.service.GameService;
import com.stayaway.service.UserService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.stayaway.Constants.ALICE;
import static com.stayaway.Constants.BOB;
import static com.stayaway.Constants.DANIEL;
import static com.stayaway.Constants.ROMA_ELIZAROV;

@SpringBootTest
@Testcontainers
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTestBase {

    private static final List<String> testLogins = List.of(ALICE, BOB, ROMA_ELIZAROV, DANIEL);

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(Constants.MONGO_IMAGE);


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add(Constants.SPRING_DATA_MONGODB_URI_PROPERTY, mongoDBContainer::getReplicaSetUrl);
    }

    @Before
    public void saveUsers() {
        testLogins.forEach(login -> userService.save(new User(login)));
    }

    @Autowired
    protected TestMvc testMvc;
    @Autowired
    protected UserService userService;
    @Autowired
    protected GameService gameService;
}
