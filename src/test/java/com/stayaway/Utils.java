package com.stayaway;

import com.stayaway.request.CreateGameRequest;

class Utils {
    static String createAndStart(TestMvc mvc, String login, String name) throws Exception {
        var createGameRequest = new CreateGameRequest();
        createGameRequest.setName(name);

        return mvc.createAndStartGame(createGameRequest, login);
    }

}
