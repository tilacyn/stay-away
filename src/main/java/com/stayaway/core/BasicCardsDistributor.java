package com.stayaway.core;

import com.stayaway.exception.StayAwayException;
import com.stayaway.model.cards.CardType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// todo do not include infected into player's hands
// todo map users number into cards counts
public class BasicCardsDistributor implements CardsDistributor {
    private static final Map<CardType, Integer> CARDS_COUNT = new HashMap<>();
    private static final String NOT_ENOUGH_CARDS_MESSAGE = "not enough cards (%d) to distribute among players (%d)";

    private final Logger logger = LoggerFactory.getLogger(BasicCardsDistributor.class);

    static {
        CARDS_COUNT.put(CardType.FLAMETHROWER, 5);
        CARDS_COUNT.put(CardType.ANALYSIS, 3);
        CARDS_COUNT.put(CardType.AXE, 2);
        CARDS_COUNT.put(CardType.YOU_BETTER_RUN, 5);
        CARDS_COUNT.put(CardType.BARRED_DOOR, 3);
        CARDS_COUNT.put(CardType.NO_BARBECUE, 3);
        CARDS_COUNT.put(CardType.IM_COMFORTABLE, 3);
        CARDS_COUNT.put(CardType.SUSPICIOUS, 8);
        CARDS_COUNT.put(CardType.CHANGE_PLACES, 5);
        CARDS_COUNT.put(CardType.NO_THANKS, 4);
        CARDS_COUNT.put(CardType.RESOLUTE, 5);
        CARDS_COUNT.put(CardType.SCARY, 4);
        CARDS_COUNT.put(CardType.WATCH_YOUR_BACK, 2);
        CARDS_COUNT.put(CardType.QUARANTINE, 2);
        CARDS_COUNT.put(CardType.MISSED, 3);
        CARDS_COUNT.put(CardType.SEDUCTION, 7);
        CARDS_COUNT.put(CardType.INFECTED, 20);
    }

    private final List<String> players;
    private final Map<String, List<CardType>> playersCards = new HashMap<>();
    private final List<CardType> deck = new ArrayList<>();
    private final List<CardType> trash = new ArrayList<>();


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
        cards.push(CardType.THE_THING);
        Collections.shuffle(players);
        var cardsIterator = cards.iterator();

        players.forEach(login -> {
            List<CardType> currentPlayerCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (!cardsIterator.hasNext()) {
                    throw StayAwayException.internalError(String.format(NOT_ENOUGH_CARDS_MESSAGE, cards.size(),
                            players.size()));
                }
                currentPlayerCards.add(cardsIterator.next());
            }
            playersCards.put(login, currentPlayerCards);
        });
        cardsIterator.forEachRemaining(deck::add);
    }

    public Map<String, List<CardType>> getPlayersCards() {
        return playersCards;
    }

    public List<CardType> getDeck() {
        return deck;
    }

    public List<CardType> getTrash() {
        return trash;
    }

    public String getTheThing() {
        return players.get(0);
    }
}