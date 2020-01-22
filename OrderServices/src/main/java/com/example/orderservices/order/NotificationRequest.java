package com.example.orderservices.order;

public class NotificationRequest {
    private final String orderId;
    private final String notificationEmail;

    public NotificationRequest(String orderId, String notificationEmail) {
        this.orderId=orderId;
        this.notificationEmail=notificationEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    @Override
    public String toString() {
        return "{orderId: ".concat(this.orderId).concat(",:notificationEmail").concat(this.notificationEmail);
    }
}
