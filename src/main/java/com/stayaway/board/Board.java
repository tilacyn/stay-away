package com.stayaway.board;

import com.stayaway.cards.Card;
import com.stayaway.cards.Deck;
import com.stayaway.cards.Trash;
import com.stayaway.move.Move;

import java.util.List;
import java.util.Map;
// this class should probably be deleted
// ig there is no need to maintain three separate Board classes
// all the board model transformation should be performed on model.BoardModel if possible
public class Board {
    private String id;
    private Map<String, Player> userID2Player;
    private List<Player> players;
    private int currentPlayer;


    private Deck deck;
    private Trash trash;

//    TODO
    public void accept(String userID, Move move) {
        
    }

    private class Player {
        private String userID;
        private boolean infected;
        private boolean theThing;
        private List<Card> cards;

        private Player next;
        private Player previous;


    }

}
