package com.stayaway.exception;

import lombok.Data;

@Data
public class FrontErrorResponse {
    private final StayAwayException.StayAwaySerializationPayload error;
}
