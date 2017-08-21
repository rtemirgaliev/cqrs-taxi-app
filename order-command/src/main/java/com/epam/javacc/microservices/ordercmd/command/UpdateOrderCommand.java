package com.epam.javacc.microservices.ordercmd.command;

import com.epam.javacc.microservices.common.order.model.OrderStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 *
 * @author Rinat Temirgaliev
 */
public class UpdateOrderCommand {

    @TargetAggregateIdentifier
    @NotNull
    @NotBlank
    private String orderId;
    @NotNull
    @NotBlank
    private String businessKey;
    @NotNull(message = "Phone is mandatory")
    @NotBlank(message = "Phone is mandatory")
    private String phone;
    @NotNull(message = "Address is mandatory")
    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotNull
    private OrderStatus status;

    public UpdateOrderCommand(String orderId, String businessKey, String phone, String address, OrderStatus status) {
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
