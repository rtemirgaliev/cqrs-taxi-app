package com.epam.javacc.microservices.common.order.event;

import com.epam.javacc.microservices.common.order.model.OrderStatus;

import java.io.Serializable;

public class OrderDeletedEvent implements Serializable {

    private static final long serialVersionUID = 8382138467663197454L;

    private String orderId;

    public OrderDeletedEvent() {
    }

    public OrderDeletedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

}
