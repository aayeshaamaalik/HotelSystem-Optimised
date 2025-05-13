package org.example;

public class NotEnoughRoomForReservationException extends Exception {
    public NotEnoughRoomForReservationException(String message) {
        super(message);
    }
}
