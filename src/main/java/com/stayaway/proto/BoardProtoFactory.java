package com.stayaway.proto;

import com.stayaway.dao.model.Board;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardProtoFactory {

    //todo implement
    public BoardProto buildProto(Board board) {
        return new BoardProto(
                List.of(),
                CardTypeProto.PANIC,
                "a",
                "b",
                null,
                List.of(),
                board.getName()
        );
    }
}
