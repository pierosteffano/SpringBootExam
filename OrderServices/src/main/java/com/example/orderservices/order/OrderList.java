package com.example.orderservices.order;

import java.util.ArrayList;
import java.util.List;

public class OrderList {

    List<Order> orders =new ArrayList<>(1);
    public OrderList(){}
    public OrderList(List<Order> orders){
        this.orders = orders;
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
