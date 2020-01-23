package com.example.orderservices.order;

public class NotificationNotSentException extends RuntimeException {

    public NotificationNotSentException(String exception) {
        super(exception);
    }
}
