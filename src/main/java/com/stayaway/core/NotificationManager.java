package com.stayaway.core;


import com.stayaway.dao.model.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationManager(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void notifyAll(Board board) {
        simpMessagingTemplate.convertAndSend(String.format("/topic/game/%s", board.getGameId()),
                new StayAwayMessage("updated"));
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class StayAwayMessage {
        private String content;
    }


}
