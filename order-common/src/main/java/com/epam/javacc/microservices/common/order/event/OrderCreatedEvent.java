package com.epam.javacc.microservices.common.order.event;

import com.epam.javacc.microservices.common.order.model.OrderStatus;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {

    private static final long serialVersionUID = 1534382475023480978L;

    private String orderId;
    private String phone;
    private String address;
    private OrderStatus status;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String phone, String address, OrderStatus status) {
        this.orderId = orderId;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
