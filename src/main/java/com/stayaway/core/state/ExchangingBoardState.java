package com.stayaway.core.state;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import com.stayaway.core.action.DefendAction;
import com.stayaway.core.action.ExchangeAction;
import com.stayaway.core.handler.DefendHandler;
import com.stayaway.core.handler.ExchangeHandler;
import com.stayaway.dao.model.builder.BoardUpdateBuilder;
import com.stayaway.model.board.state.BoardStatus;
import lombok.Data;
import org.springframework.data.annotation.PersistenceCreator;


public class ExchangingBoardState extends AbstractBoardState implements ExchangeHandler, DefendHandler {

    private final List<Transition> transitions;

    @PersistenceCreator
    public ExchangingBoardState(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public ExchangingBoardState(String first, String second) {
        transitions = Stream.of(first, second)
                .map(Transition::new)
                .collect(Collectors.toList());
    }

    @Override
    public void defend(DefendAction action) {
        //todo implement later
    }

    @Override
    public void exchange(ExchangeAction action) {
        validateAndSaveChosen(action);
    }

    @Override
    public boolean checkPreconditionsFulfilled() {
        return transitions.stream()
                .map(Transition::getCard)
                .allMatch(Objects::nonNull);
    }

    @Override
    protected void doTransform() {
        builder = new BoardUpdateBuilder(board, new DrawingBoardState())
                .nextPlayer()
                .nextTurn();

        for (int i = 0; i < transitions.size(); i++) {
            String from = transitions.get(i).getInitiator();
            String to = transitions.get((i + 1) % transitions.size()).getInitiator();
            int card = Objects.requireNonNull(transitions.get(i).getCard(), "card should be specified");
            builder.transit(from, to, card);
        }
    }

    @Override
    public BoardStatus getStatus() {
        return BoardStatus.EXCHANGING;
    }

    @Override
    public void doRegisterHandlers() {
        board.setExchangeHandler(this);
        board.setDefendHandler(this);
    }

    private void validateAndSaveChosen(ExchangeAction action, Transition transition) {
        if (transition.getCard() != null) {
            throw ExceptionUtils.attemptToChoseCardToChangeTwice(action.getLogin(), transition.getCard(),
                    action.getCard());
        }
        validateCardNumber(action.getLogin(), action.getCard());
        transition.setCard(action.getCard());
    }

    private void validateAndSaveChosen(ExchangeAction action) {
        transitions.stream()
                .filter(transition -> Objects.equals(transition.getInitiator(), action.getLogin()))
                .findFirst()
                .ifPresentOrElse(
                        transition -> validateAndSaveChosen(action, transition),
                        () -> {
                            throw ExceptionUtils.playerActionNotExpected(action.getLogin());
                        }
                );
    }

    @Data
    private static class Transition {
        @NonNull
        private final String initiator;
        @Nullable
        private Integer card;
    }

}
