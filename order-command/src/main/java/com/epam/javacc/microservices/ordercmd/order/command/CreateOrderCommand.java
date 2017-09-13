package com.epam.javacc.microservices.ordercmd.order.command;

import com.epam.javacc.microservices.common.order.model.OrderStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 *
 * @author Rinat Temirgaliev
 */
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    @NotNull(message = "Phone is mandatory")
    @NotBlank(message = "Phone can not be blank")
    private String phone;
    @NotNull(message = "Address is mandatory")
    @NotBlank(message = "Address can not be blank")
    private String address;
    @NotNull
    private OrderStatus status;

    public CreateOrderCommand(String orderId, String phone, String address, OrderStatus status) {
        this.orderId = orderId;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public CreateOrderCommand(String phone, String address, OrderStatus status) {
        this.orderId = UUID.randomUUID().toString();
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public CreateOrderCommand(String phone, String address) {
        this.orderId = UUID.randomUUID().toString();
        this.phone = phone;
        this.address = address;
        this.status = OrderStatus.PUBLISHED;
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
