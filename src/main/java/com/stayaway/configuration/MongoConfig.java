package com.stayaway.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.DBObject;
import com.mongodb.MongoClientSettings;
import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.board.player.PlayerType;
import com.stayaway.model.cards.CardType;
import com.stayaway.utils.PlayerUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;


@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.applyConnectionString(new ConnectionString(mongoUri));
    }

    @Override
    protected String getDatabaseName() {
        return "stayaway";
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new PlayerWritingConverter(),
                new PlayerReadingConverter()
        ));
    }

    @WritingConverter
    private static class PlayerWritingConverter implements Converter<Player, DBObject> {

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

    @ReadingConverter
    private static class PlayerReadingConverter implements Converter<ArrayList<Document>, Player> {

        @Override
        public Player convert(ArrayList<Document> documents) {
            List<Player> players = new ArrayList<>();
            documents.stream()
                    .map(this::objectToPlayer)
                    .forEach(players::add);
            return PlayerUtils.fromList(players, Direction.RIGHT);
        }

        private Player objectToPlayer(Document document) {
            return new Player(
                    (String) document.get("login"),
                    PlayerType.valueOf((String) document.get("type")),
                    ((List<?>) document.get("cards")).stream()
                            .map(obj -> (String) obj)
                            .map(CardType::valueOf).collect(Collectors.toList())
            );
        }
    }
}
