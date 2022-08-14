package com.stayaway.dao.model;

import lombok.Data;

@Data
public class BoardStatus {
    private final StatusPayload payload;
    private final Move move;
}
