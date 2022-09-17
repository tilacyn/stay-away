package com.stayaway.dao.model.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.stayaway.model.board.Direction;
import com.stayaway.model.board.player.Player;
import com.stayaway.model.board.player.PlayerType;
import com.stayaway.model.cards.CardType;
import com.stayaway.utils.PlayerUtils;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class PlayerReadingConverter implements Converter<ArrayList<Document>, Player> {

    @Override
    public Player convert(ArrayList<Document> documents) {
        List<Player> players = new ArrayList<>();
        documents.stream()
                .map(this::objectToPlayer)
                .forEach(players::add);
        return PlayerUtils.fromList(players, Direction.INIT);
    }

    private Player objectToPlayer(Document document) {
        return new Player(
                document.getString("login"),
                PlayerType.valueOf(document.getString("type")),
                document.getList("cards", String.class).stream()
                        .map(CardType::valueOf).collect(Collectors.toList())
        );
    }
}