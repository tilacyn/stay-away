package com.stayaway.core.action.factory;

import com.stayaway.core.action.DrawAction;


public class DrawActionFactory {
    public DrawAction create(String login) {
        return new DrawAction(login);
    }
}
