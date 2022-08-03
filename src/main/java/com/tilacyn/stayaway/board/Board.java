package com.tilacyn.stayaway.board;

import com.tilacyn.stayaway.cards.Card;
import com.tilacyn.stayaway.cards.Deck;
import com.tilacyn.stayaway.cards.Trash;
import com.tilacyn.stayaway.move.Move;
import com.tilacyn.stayaway.user.UserID;

import java.util.List;
import java.util.Map;

public class Board {
    private String id;
    private Map<UserID, Player> userID2Player;
    private List<Player> players;
    private int currentPlayer;


    private Deck deck;
    private Trash trash;

//    TODO
    public void accept(UserID userID, Move move) {
        
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
