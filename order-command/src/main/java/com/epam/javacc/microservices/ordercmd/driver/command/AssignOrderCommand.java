package com.epam.javacc.microservices.ordercmd.driver.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AssignOrderCommand {

    @TargetAggregateIdentifier
    private String driverId;
    private String orderId;

    public AssignOrderCommand(String driverId, String orderId) {
        this.driverId = driverId;
        this.orderId = orderId;
    }


    public String getDriverId() {
        return driverId;
    }
    public String getOrderId() {
        return orderId;
    }


}
