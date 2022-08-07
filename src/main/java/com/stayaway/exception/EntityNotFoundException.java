package com.stayaway.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityNotFoundException extends Throwable {

    private final String entityId;
    private final EntityType entityType;

    public static EntityNotFoundException board(String id) {
        return new EntityNotFoundException(id, EntityType.BOARD);
    }

    public static EntityNotFoundException user(String id) {
        return new EntityNotFoundException(id, EntityType.USER);
    }

    private enum EntityType {
        BOARD("board"),
        USER("user");

        private final String type;

        EntityType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
