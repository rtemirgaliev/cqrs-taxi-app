package com.epam.javacc.microservices.common.order.event;

import com.epam.javacc.microservices.common.order.model.OrderStatus;

import java.io.Serializable;

public class OrderUpdatedEvent implements Serializable {

    private static final long serialVersionUID = -3057624125822179714L;

    private String orderId;
    private String businessKey;
    private String phone;
    private String address;
    private OrderStatus status;

    public OrderUpdatedEvent() {
    }

    public OrderUpdatedEvent(String orderId, String businessKey, String phone, String address, OrderStatus status) {
        this.orderId = orderId;
        this.businessKey = businessKey;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getBusinessKey() {
        return businessKey;
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
