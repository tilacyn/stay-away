package com.stayaway.core;


import com.stayaway.core.action.*;
import com.stayaway.dao.model.Board;
import com.stayaway.service.GameService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
public class GameplayManager {
    private final NotificationManager notificationManager;
    private final GameService gameService;

    public GameplayManager(NotificationManager notificationManager, GameService gameService) {
        this.notificationManager = notificationManager;
        this.gameService = gameService;
    }

    public void exchange(ExchangeAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getExchangeHandler())
                    .orElseThrow(UnsupportedOperationException::new);
            handler.exchange(action);
        });
    }

    public void defend(DefendAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getDefendHandler())
                    .orElseThrow(UnsupportedOperationException::new);
            handler.defend(action);
        });
    }

    public void draw(DrawAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getDrawHandler())
                    .orElseThrow(UnsupportedOperationException::new);
            handler.draw(action);
        });
    }

    public void discard(DiscardAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getDiscardHandler())
                    .orElseThrow(UnsupportedOperationException::new);
            handler.discard(action);
        });
    }

    public void play(PlayAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getPlayHandler())
                    .orElseThrow(UnsupportedOperationException::new);
            handler.play(action);
        });
    }

    public void viewCards(ViewCardsAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getViewCardsHandler())
                    .orElseThrow(UnsupportedOperationException::new);
            handler.viewCards(action);
        });
    }

    public void process(String gameID, Consumer<Board> transition) {
        var newBoard = processAndSave(gameID, transition);
        notificationManager.notifyAll(newBoard);
    }

    //    TRANSACTIONAL SERIALIZABLE
    public Board processAndSave(String gameID, Consumer<Board> changeState) {
        Board board = gameService.getCurrentBoard(gameID);
        changeState.accept(board);

        var state = board.getBoardState();

        while (state.checkPreconditionsFulfilled()) {
            board = state.transform();
            state = board.getBoardState();
        }

        gameService.saveBoard(board);
        return board;
    }

}
