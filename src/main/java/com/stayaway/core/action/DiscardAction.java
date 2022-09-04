package com.stayaway.core.action;

import lombok.Data;

@Data
public class DiscardAction {
    private final String login;
    private final int cardNumber;
}
