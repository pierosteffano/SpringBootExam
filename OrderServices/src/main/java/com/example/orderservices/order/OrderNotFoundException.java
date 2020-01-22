package com.example.orderservices.order;

public class OrderNotFoundException  extends RuntimeException {

    public OrderNotFoundException(String exception) {
        super(exception);
    }
}
