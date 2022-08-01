package com.stayaway.board;

import com.stayaway.cards.Card;
import com.stayaway.cards.Deck;
import com.stayaway.cards.Trash;
import com.stayaway.move.Move;
import com.stayaway.user.UserID;

import java.util.List;
import java.util.Map;

public class Board {
    private String id;
    private Map<UserID, Player> players;

    private Deck deck;
    private Trash trash;

//    TODO
    public void makeMove(UserID userID, Move move) {

    }

    private class Player {
        private UserID userID;
        private boolean infected;
        private boolean theThing;
        private List<Card> cards;

        private Player next;
        private Player previous;


    }

}
