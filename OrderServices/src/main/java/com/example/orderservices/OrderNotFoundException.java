package com.example.orderservices;

public class OrderNotFoundException  extends RuntimeException {

    public OrderNotFoundException(String exception) {
        super(exception);
    }
}
