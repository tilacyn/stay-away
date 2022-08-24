package com.stayaway.dao.model.builder;

import com.stayaway.dao.model.Board;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@AllArgsConstructor
public class BoardUpdateBuilder {
    private final Board board;

    public Board build() {
        return board.toBuilder()
                .id(RandomStringUtils.randomAlphabetic(10))
                .stage(board.getStage() + 1)
                .build();
    }

}
