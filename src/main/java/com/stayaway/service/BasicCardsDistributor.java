package com.stayaway.service;

import com.stayaway.dao.model.Card;
import com.stayaway.dao.model.Deck;
import com.stayaway.exception.StayAwayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class BasicCardsDistributor implements CardsDistributor {
    private static final Map<Card, Integer> CARDS_COUNT = new HashMap<>();
    private static final String NOT_ENOUGH_CARDS_MESSAGE = "not enough cards (%d) to distribute among players (%d)";

    private final Logger logger = LoggerFactory.getLogger(BasicCardsDistributor.class);

    static {
        CARDS_COUNT.put(Card.FLAMETHROWER, 50);
        CARDS_COUNT.put(Card.NO_THANKS, 50);
    }

    private List<String> players;
    private final Map<String, List<Card>> playersCards = new HashMap<>();
    private final Deck deck = new Deck(new ArrayList<>());


    public BasicCardsDistributor(Set<String> players) {
        if (players.isEmpty()) {
            throw new IllegalArgumentException("players are empty");
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
