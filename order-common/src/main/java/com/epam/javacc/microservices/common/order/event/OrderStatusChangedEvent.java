package com.epam.javacc.microservices.common.order.event;

import com.epam.javacc.microservices.common.order.model.OrderStatus;

import java.io.Serializable;

public class OrderStatusChangedEvent implements Serializable {

    private static final long serialVersionUID = 3245556924732759230L;

    private String orderId;
    private OrderStatus status;
    private String transactionId;

    public OrderStatusChangedEvent() {
    }

    public OrderStatusChangedEvent(String orderId, OrderStatus status, String transactionId) {
        this.orderId = orderId;
        this.status = status;
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public String getTransactionId() {
        return transactionId;
    }
}
