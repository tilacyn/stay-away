package com.stayaway.core.action;

import lombok.Data;

@Data
public class PlayAction {
    private final String login;
    private final int cardNumber;
    private final String target;
}
