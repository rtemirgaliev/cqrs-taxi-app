package com.epam.javacc.microservices.drivercmd.driver.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class ChangeDriverOrderCommand {

    @TargetAggregateIdentifier
    private String driverId;
    private String assignedOrderId;
    private String transactionId;

    public ChangeDriverOrderCommand(String driverId, String assignedOrderId, String assignmentId) {
        this.driverId = driverId;
        this.assignedOrderId = assignedOrderId;
        this.transactionId = assignmentId;
    }


    public String getDriverId() {
        return driverId;
    }
    public String getAssignedOrderId() {
        return assignedOrderId;
    }
    public String getTransactionId() {
        return transactionId;
    }
}
