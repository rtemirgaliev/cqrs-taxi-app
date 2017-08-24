package com.epam.javacc.microservices.ordercmd.order.command;

import com.epam.javacc.microservices.common.order.model.OrderStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class ChangeOrderStatusCommand {

    @TargetAggregateIdentifier
    @NotNull
    @NotBlank
    private String orderId;
    @NotNull
    private OrderStatus status;
    private String transactionId;

    public ChangeOrderStatusCommand(String orderId, OrderStatus status, String transactionId) {
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
