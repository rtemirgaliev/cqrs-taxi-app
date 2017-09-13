package com.epam.javacc.microservices.ordercmd.order.command;

import com.epam.javacc.microservices.common.order.model.OrderStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Rinat Temirgaliev
 */
public class DeleteOrderCommand {

    @TargetAggregateIdentifier
    @NotNull
    @NotBlank
    private String orderId;

    public DeleteOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
