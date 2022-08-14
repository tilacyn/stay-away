package com.stayaway.manager;


import com.stayaway.dao.model.Board;
import com.stayaway.exception.StayAwayException;
import com.stayaway.manager.actions.UserAction;
import com.stayaway.manager.transformations.BoardTransformation;
import com.stayaway.manager.transformations.ChoosingCardTransformation;
import com.stayaway.manager.transformations.ExchangingTransformation;
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

    public void process(UserAction action, String gameID) {
        var newBoard = processAndSave(action, gameID);
        notificationManager.notifyAll(newBoard);
    }

    //    TRANSACTIONAL SERIALIZABLE
    public Board processAndSave(UserAction action, String gameID) {
        Board state = gameService.getCurrentBoard(gameID);
        //todo generate one of actions, apply to state, apply post-transition, call switch to next state(in the loop)
        BoardTransformation transformation;
        switch (state.getBoardState().getStatus()) {
            case EXCHANGING:
                transformation = new ExchangingTransformation(action, state);
                break;
            case CHOOSING_CARD_TO_PLAY_OR_DISCARD:
                transformation = new ChoosingCardTransformation(action, state);
                break;
            default:
                throw StayAwayException.internalError("Unknown board status");
//          and so on
        }
        state = transformation.run(state);
        gameService.saveBoard(state);
        return state;
    }
}
