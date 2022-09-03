package com.stayaway.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StayAwayExceptionHandler {
    @Value("${logging.stacktrace}")
    private boolean stacktraceLogging;
    private final Logger logger = LoggerFactory.getLogger(StayAwayExceptionHandler.class);

    @ExceptionHandler(StayAwayException.class)
    public ResponseEntity<FrontErrorResponse> handleStayAwayException(StayAwayException exception) {
        if (stacktraceLogging) {
            logger.error("Stay-away exception caught: {}", exception.getMessage());
        } else {
            logger.error("Stay-away exception caught", exception);
        }
        return new ResponseEntity<>(new FrontErrorResponse(exception.getPayload()), exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FrontErrorResponse> handleUnknownException(Exception exception) {
        logger.error("Unknown exception caught", exception);
        return new ResponseEntity<>(new FrontErrorResponse(StayAwayException.unknownInternalError(exception.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
