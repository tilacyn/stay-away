package com.stayaway.dao.model.converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.stayaway.core.state.BoardState;
import com.stayaway.exception.StayAwayException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class BoardStateWritingConverter implements Converter<BoardState, DBObject> {

    @Override
    public DBObject convert(BoardState state) {
        switch (state.getStatus()) {
            case DRAWING:
                return convertDrawing(state);
            case CHOOSING_CARD_TO_PLAY_OR_DISCARD:
                return convertChoosingCard(state);
            case EXCHANGING:
                return convertExchanging(state);
            default:
                throw StayAwayException.internalError(String.format("unsupported status %s", state.getStatus()));
        }
    }

    private DBObject convertExchanging(BoardState state) {
        DBObject object = new BasicDBObject();
        object.put("status", state.getStatus());
        return object;
    }

    //    TODO
    private DBObject convertChoosingCard(BoardState state) {
        DBObject object = new BasicDBObject();
        object.put("status", state.getStatus());
        return object;
    }

    private DBObject convertDrawing(BoardState state) {
        DBObject object = new BasicDBObject();
        object.put("status", state.getStatus());
        return object;
    }

}