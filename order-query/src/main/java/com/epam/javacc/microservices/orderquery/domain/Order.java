package com.epam.javacc.microservices.orderquery.domain;

import com.epam.javacc.microservices.common.order.event.OrderCreatedEvent;
import com.epam.javacc.microservices.common.order.model.OrderStatus;

import javax.persistence.*;

@Entity(name = "Orders")
public class Order {

    @Id
    private String orderId;
    private String phone;
    private String address;
    @Enumerated
    private OrderStatus status;
    @Version
    private Long optLockVersion;


    public Order() {
    }

    public Order(String orderId, String phone, String address, OrderStatus status) {
        this.orderId = orderId;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public Order(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.phone = event.getPhone();
        this.address = event.getAddress();
        this.status = event.getStatus();
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
