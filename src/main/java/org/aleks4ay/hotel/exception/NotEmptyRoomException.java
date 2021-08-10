package org.aleks4ay.hotel.exception;

public class NotEmptyRoomException extends RuntimeException {
    public NotEmptyRoomException(String message) {
        super(message);
    }
}