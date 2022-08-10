package com.stayaway.response.factory;

import com.stayaway.dao.model.User;
import com.stayaway.response.PlayerResponse;
import org.springframework.stereotype.Component;


//todo introduce ResponseBuilder interface and scan all instances in config (something like this)
@Component
public class PlayerResponseFactory {
    public PlayerResponse buildProto(User user) {
        return new PlayerResponse(user.getId(), user.getLogin());
    }
}
