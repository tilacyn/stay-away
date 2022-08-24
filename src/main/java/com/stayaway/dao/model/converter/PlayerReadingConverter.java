package com.stayaway.dao.model.converter;

import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.board.player.PlayerType;
import com.stayaway.model.cards.CardType;
import com.stayaway.utils.PlayerUtils;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ReadingConverter
public class PlayerReadingConverter implements Converter<ArrayList<Document>, Player> {

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