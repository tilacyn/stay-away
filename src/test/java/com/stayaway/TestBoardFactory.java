package com.stayaway;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.stayaway.core.CardsDistributor;
import com.stayaway.dao.model.Game;
import com.stayaway.model.board.player.Player;
import com.stayaway.service.BasicBoardFactory;

import static com.stayaway.Constants.ALICE;
import static com.stayaway.Constants.BOB;
import static com.stayaway.Constants.DANIEL;
import static com.stayaway.Constants.ROMA_ELIZAROV;

public class TestBoardFactory extends BasicBoardFactory {
    private static final List<String> logins = List.of(ALICE, BOB, ROMA_ELIZAROV, DANIEL);

    protected Set<String> getGameUserLogins(Game game) {
        return Set.copyOf(logins);
    }

    protected List<Player> createPlayers(CardsDistributor cardsDistributor) {
        var players = super.createPlayers(cardsDistributor);
        return reorderPlayers(players);
    }

    private List<Player> reorderPlayers(List<Player> players) {
        var login2player = players.stream().collect(Collectors.groupingBy(Player::getLogin));
        var reorderedPlayers = new ArrayList<Player>();

        logins.forEach(login -> reorderedPlayers.add(login2player.get(login).get(0)));
        return reorderedPlayers;
    }
}
