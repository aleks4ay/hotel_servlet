package org.aleks4ay.hotel.exception;

public class CannotSaveException extends RuntimeException {
    public CannotSaveException(String message) {
        super(message);
    }
}