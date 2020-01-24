package com.example.orderservices.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonInclude(Include.NON_NULL)
    private Integer orderId;
    @JsonInclude(Include.NON_NULL)
    private String shopperRut;
    @JsonInclude(Include.NON_NULL)
    private String shopperEmail;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "orderItemId")
    private Set<OrderItem> orderItems=new HashSet<>();
    @JsonInclude(Include.NON_NULL)
    private Float orderTotal;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonInclude(Include.NON_NULL)
    private Date creationDate;
    @JsonInclude(Include.NON_NULL)
    private String orderStatus;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getShopperRut() {
        return shopperRut;
    }

    public void setShopperRut(String shopperRut) {
        this.shopperRut = shopperRut;
    }

    public String getShopperEmail() {
        return shopperEmail;
    }

    public void setShopperEmail(String shopperEmail) {
        this.shopperEmail = shopperEmail;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Float orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
