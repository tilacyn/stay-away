package com.stayaway.dao.model.converter;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.utils.PlayerUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class PlayerWritingConverter implements Converter<Player, DBObject> {

    @Override
    public DBObject convert(Player player) {
        BasicDBList list = new BasicDBList();
        PlayerUtils.getPlayersList(player, Direction.RIGHT)
                .stream().map(this::playerToObject)
                .forEach(list::add);
        return list;
    }


    private DBObject playerToObject(Player player) {
        DBObject object = new BasicDBObject();
        object.put("login", player.getLogin());
        object.put("type", player.getType());
        object.put("cards", player.getCards());
        return object;
    }
}