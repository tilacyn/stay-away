package com.stayaway.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayAwayException extends RuntimeException {
    private final StayAwaySerializationPayload payload;
    private final HttpStatus httpStatus;


    public interface StayAwaySerializationPayload {
        String getMessage();
    }

    public static StayAwayException notFound(String id, EntityType entityType) {
        return new StayAwayException(new StayAwayEntityNotFoundPayload(id, entityType), HttpStatus.NOT_FOUND);
    }

    public static StayAwayException internalError(String message) {
        return new StayAwayException(new StayAwayInternalErrorPayload(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static StayAwayException wrongInputFormat(String property) {
        return new StayAwayException(new StayAwayWrongInputFormatPayload(property), HttpStatus.PRECONDITION_FAILED);
    }

    public static StayAwayException missingInput(String property) {
        return new StayAwayException(new StayAwayMissingInputPayload(property), HttpStatus.PRECONDITION_REQUIRED);
    }

    public static StayAwayException conflict(String message) {
        return new StayAwayException(new StayAwayConflictFormatPayload(message), HttpStatus.CONFLICT);
    }

    public static StayAwaySerializationPayload unknownInternalError(String message) {
        return new StayAwayInternalErrorPayload("Unknown internal error: " + message);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class StayAwayEntityNotFoundPayload implements StayAwaySerializationPayload {
        private final String entityId;
        private final EntityType entityType;

        @Override
        public String getMessage() {
            return entityType + "[" + entityId + "] not found";
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    @Getter
    @AllArgsConstructor
    private static class StayAwayInternalErrorPayload implements StayAwaySerializationPayload {
        private final String message;

        @Override
        public String toString() {
            return getMessage();
        }
    }

    @Getter
    @AllArgsConstructor
    private static class StayAwayWrongInputFormatPayload implements StayAwaySerializationPayload {
        private final String property;

        @Override
        public String getMessage() {
            return "Wrong input format for property: " + property;
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    @Getter
    @AllArgsConstructor
    private static class StayAwayConflictFormatPayload implements StayAwaySerializationPayload {
        private final String message;

        @Override
        public String getMessage() {
            return "Conflict: " + message;
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    @Getter
    @AllArgsConstructor
    private static class StayAwayMissingInputPayload implements StayAwaySerializationPayload {
        private final String property;

        @Override
        public String getMessage() {
            return "Missing input: " + property;
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    public enum EntityType {
        BOARD,
        USER,
        GAME,
    }

    @Override
    public String toString() {
        return "StayAwayException{" +
                "payload=" + payload +
                ", httpStatus=" + httpStatus +
                '}';
    }

    @Override
    public String getMessage() {
        return payload.getMessage();
    }
}
