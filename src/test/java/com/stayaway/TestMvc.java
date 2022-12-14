package com.stayaway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stayaway.request.CreateGameRequest;
import com.stayaway.request.DiscardRequest;
import com.stayaway.request.ExchangeRequest;
import com.stayaway.request.PlayRequest;
import com.stayaway.response.BoardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class TestMvc {
    private final MockMvc mvc;

    private String getGameIdFromLocation(String location) {
        var parts = location.split("/");
        assertTrue(parts.length > 1);
        return parts[parts.length - 1];
    }

    String createAndStartGame(CreateGameRequest request, String login) throws Exception {
        String gameId = performCreate(request, login);
        performStart(gameId, login);
        return gameId;
    }

    String performCreate(CreateGameRequest request, String login) throws Exception {
        String createGameRequestJson = new ObjectMapper().writeValueAsString(request);
        AtomicReference<String> locationRef = new AtomicReference<>();

        mvc.perform(post("/v1/create")
                        .with(user(login))
                        .content(createGameRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andDo(res -> locationRef.set(res.getResponse().getHeader("location")));
        String location = locationRef.get();
        return getGameIdFromLocation(location);
    }

    void performStart(String gameId, String login) throws Exception {
        mvc.perform(post("/v1/game/" + gameId + "/start")
                        .with(user(login))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
    }

    BoardResponse performGetBoard(String gameId, String login) throws Exception {
        AtomicReference<String> boardResponseStringRef = new AtomicReference<>();
        mvc.perform(get("/v1/game/" + gameId + "/board")
                        .with(user(login))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(res -> boardResponseStringRef.set(res.getResponse().getContentAsString()));
        var boardResponseString = boardResponseStringRef.get();
        return new ObjectMapper().readValue(boardResponseString, BoardResponse.class);
    }

    void performDraw(String gameId, String login) throws Exception {
        mvc.perform(post("/v1/game/" + gameId + "/draw")
                        .with(user(login))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    void performDiscard(String gameId, String login, DiscardRequest request) throws Exception {
        String discardJson = new ObjectMapper().writeValueAsString(request);
        mvc.perform(post("/v1/game/" + gameId + "/discard")
                        .with(user(login))
                        .content(discardJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    void expectDiscardStatus(String gameId, String login, DiscardRequest request, int status) throws Exception {
        String discardJson = new ObjectMapper().writeValueAsString(request);
        mvc.perform(post("/v1/game/" + gameId + "/discard")
                        .with(user(login))
                        .content(discardJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(status));
    }

    void expectPlayStatus(String gameId, String login, PlayRequest request, int status) throws Exception {
        String playJson = new ObjectMapper().writeValueAsString(request);
        mvc.perform(post("/v1/game/" + gameId + "/play")
                        .with(user(login))
                        .content(playJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(status));
    }

    void expectDrawNotAllowed(String gameId, String login) throws Exception {
        mvc.perform(post("/v1/game/" + gameId + "/draw")
                        .with(user(login))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    void expectExchangeStatus(String gameId, String login, ExchangeRequest request, int status) throws Exception {
        String discardJson = new ObjectMapper().writeValueAsString(request);
        mvc.perform(post("/v1/game/" + gameId + "/exchange")
                        .with(user(login))
                        .content(discardJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(status));
    }
}
