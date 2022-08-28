package com.stayaway.dao.model.builder;

import com.stayaway.dao.model.Board;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@AllArgsConstructor
public class BoardUpdateBuilder {
    private final Board board;

    public Board build() {
        return Board.builder()
                .deck(board.getDeck())
                .trash(board.getTrash())
                .gameId(board.getGameId())
//                todo change turn
                .turn(board.getTurn())
                .direction(board.getDirection())
//                todo change player
                .currentPlayer(board.getCurrentPlayer())
                .id(RandomStringUtils.randomAlphabetic(10))
                .stage(board.getStage() + 1)
                .build();
    }

}
