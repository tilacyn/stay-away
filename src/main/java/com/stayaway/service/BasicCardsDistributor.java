package com.stayaway.service;

import com.stayaway.dao.model.Card;
import com.stayaway.dao.model.Deck;
import com.stayaway.exception.StayAwayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// todo do not include infected into player's hands
// todo map users number into cards counts
public class BasicCardsDistributor implements CardsDistributor {
    private static final Map<Card, Integer> CARDS_COUNT = new HashMap<>();
    private static final String NOT_ENOUGH_CARDS_MESSAGE = "not enough cards (%d) to distribute among players (%d)";

    private final Logger logger = LoggerFactory.getLogger(BasicCardsDistributor.class);

    static {
        CARDS_COUNT.put(Card.FLAMETHROWER, 5);
        CARDS_COUNT.put(Card.ANALYSIS, 3);
        CARDS_COUNT.put(Card.AXE, 2);
        CARDS_COUNT.put(Card.YOU_BETTER_RUN, 5);
        CARDS_COUNT.put(Card.BARRED_DOOR, 3);
        CARDS_COUNT.put(Card.NO_BARBECUE, 3);
        CARDS_COUNT.put(Card.IM_COMFORTABLE, 3);
        CARDS_COUNT.put(Card.SUSPICIOUS, 8);
        CARDS_COUNT.put(Card.CHANGE_PLACES, 5);
        CARDS_COUNT.put(Card.NO_THANKS, 4);
        CARDS_COUNT.put(Card.RESOLUTE, 5);
        CARDS_COUNT.put(Card.SCARY, 4);
        CARDS_COUNT.put(Card.WATCH_YOUR_BACK, 2);
        CARDS_COUNT.put(Card.QUARANTINE, 2);
        CARDS_COUNT.put(Card.MISSED, 3);
        CARDS_COUNT.put(Card.SEDUCTION, 7);
        CARDS_COUNT.put(Card.INFECTED, 20);
    }

    private List<String> players;
    private final Map<String, List<Card>> playersCards = new HashMap<>();
    private final Deck deck = new Deck(new ArrayList<>());


    public BasicCardsDistributor(Set<String> players) {
        if (players.isEmpty()) {
            throw StayAwayException.internalError("cannot distribute cards: there are no players");
        }
        this.players = new ArrayList<>(players);
    }

    public void generate() {
        var cards = CARDS_COUNT.entrySet().stream()
                .flatMap(entry -> Stream.iterate(entry.getKey(), card -> card).limit(entry.getValue()))
                .collect(Collectors.toCollection(LinkedList::new));
        Collections.shuffle(cards);
        cards.push(Card.THE_THING);
        Collections.shuffle(players);
        var cardsIterator = cards.iterator();

        players.forEach(login -> {
            List<Card> currentPlayerCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (!cardsIterator.hasNext()) {
                    throw StayAwayException.internalError(String.format(NOT_ENOUGH_CARDS_MESSAGE, cards.size(), players.size()));
                }
                currentPlayerCards.add(cardsIterator.next());
            }
            playersCards.put(login, currentPlayerCards);
        });
        cardsIterator.forEachRemaining(deck.getCards()::add);
    }

    public Map<String, List<Card>> getPlayersCards() {
        return playersCards;
    }

    public Deck getDeck() {
        return deck;
    }

    public String getTheThing() {
        return players.get(0);
    }
}
