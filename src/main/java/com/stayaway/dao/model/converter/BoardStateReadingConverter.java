package com.stayaway.dao.model.converter;

import com.stayaway.core.state.BoardState;
import com.stayaway.core.state.ChoosingCardBoardState;
import com.stayaway.core.state.DrawingBoardState;
import com.stayaway.exception.StayAwayException;
import com.stayaway.model.board.state.BoardStatus;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class BoardStateReadingConverter implements Converter<Document, BoardState> {

    @Override
    public BoardState convert(Document document) {
        var boardStatus = BoardStatus.valueOf((String) document.get("status"));
        BoardState boardState;
        switch (boardStatus) {
            case DRAWING:
                boardState = convertDrawing(document);
                break;
            case CHOOSING_CARD_TO_PLAY_OR_DISCARD:
                boardState = convertChoosingCard(document);
                break;
            default:
                throw StayAwayException.internalError("cannot deserialize board");
        }
        return boardState;
    }

    //    TODO
    private BoardState convertChoosingCard(Document document) {
        return new ChoosingCardBoardState();
    }

    private BoardState convertDrawing(Document document) {
        return new DrawingBoardState();
    }
}
