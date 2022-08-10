package com.stayaway.response;

import com.stayaway.model.Game;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GameResponse {
    private final String id;
    private final String name;
    private final Set<String> userIDs;
    private final Game.GameStatus status;
}
