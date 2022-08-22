package com.stayaway.response;

import java.util.Set;

import com.stayaway.dao.model.Game;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResponse {
    private final String id;
    private final String name;
    private final Set<String> userIDs;
    private final Game.GameStatus status;
}
