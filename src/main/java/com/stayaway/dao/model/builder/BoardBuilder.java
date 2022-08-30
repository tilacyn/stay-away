package com.stayaway.dao.model.builder;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class BoardBuilder {
    protected final String id;
    protected final String gameId;
    protected final int stage;

    public BoardBuilder(String gameId, int stage) {
        this.id = RandomStringUtils.randomAlphabetic(10);
        this.gameId = gameId;
        this.stage = stage;
    }
}
