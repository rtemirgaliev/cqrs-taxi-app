package com.epam.javacc.microservices.drivercmd.assignment.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class RevertAssignOrderCommand {

    @TargetAggregateIdentifier
    private final String assignmentId;
    private final String orderId;
    private final String driverId;

    public RevertAssignOrderCommand(String assignmentId, String orderId, String driverId) {
        this.assignmentId = assignmentId;
        this.orderId = orderId;
        this.driverId = driverId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDriverId() {
        return driverId;
    }
}
