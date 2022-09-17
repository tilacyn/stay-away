package com.stayaway.core;


import java.util.Optional;
import java.util.function.Consumer;

import com.stayaway.core.action.ConfirmAction;
import com.stayaway.core.action.DefendAction;
import com.stayaway.core.action.DiscardAction;
import com.stayaway.core.action.DrawAction;
import com.stayaway.core.action.ExchangeAction;
import com.stayaway.core.action.PlayAction;
import com.stayaway.dao.model.Board;
import com.stayaway.exception.StayAwayException;
import com.stayaway.service.GameService;
import org.springframework.stereotype.Component;

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
                    .orElseThrow(() -> gameplayActionNotAllowed(board, "exchange"));
            handler.exchange(action);
        });
    }

    public void defend(DefendAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getDefendHandler())
                    .orElseThrow(() -> gameplayActionNotAllowed(board, "defend"));
            handler.defend(action);
        });
    }

    public void draw(DrawAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getDrawHandler())
                    .orElseThrow(() -> gameplayActionNotAllowed(board, "draw"));
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

    public void confirm(ConfirmAction action, String gameId) {
        process(gameId, board -> {
            var handler = Optional.ofNullable(board.getConfirmHandler())
                    .orElseThrow(UnsupportedOperationException::new);
            handler.confirm(action);
        });
    }

    private void process(String gameID, Consumer<Board> transition) {
        var newBoard = processAndSave(gameID, transition);
        notificationManager.notifyAll(newBoard);
    }

    //    TRANSACTIONAL SERIALIZABLE
    public Board processAndSave(String gameID, Consumer<Board> changeState) {
        Board board = gameService.getCurrentBoard(gameID);
        board.registerHandlers();
        changeState.accept(board);

        var state = board.getBoardState();

        while (state.checkPreconditionsFulfilled()) {
            board = state.transform();
            state = board.getBoardState();
        }

        gameService.saveBoard(board);
        return board;
    }

    private static StayAwayException gameplayActionNotAllowed(Board b, String actionName) {
        var status = b.getBoardState().getStatus();
        return StayAwayException.conflict(String.format("method %s is not allowed, current status: %s", actionName,
                status));
    }
}
