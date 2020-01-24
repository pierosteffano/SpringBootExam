package com.example.notificationservices.notification;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity(name="NOTIFICATIONS")
public class Notification {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "notificationId")
    private Integer notificationId;
    private String orderId;
    @Email
    private String notificationEmail;
    private String notificationStatus;
    public Notification(){}
    public Notification(String orderId,String notificationEmail) {

        this.orderId=orderId;
        this.notificationEmail=notificationEmail;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    @Override
    public String toString() {
        return "{notificationEmail: ".concat(this.notificationEmail).concat(",orderId: ").concat(this.orderId).concat("}");
    }
}
