package com.epam.javacc.microservices.drivercmd.assignment.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AssignOrderInOrderAggregateCommand {

    private final String assignmentId;
    @TargetAggregateIdentifier
    private final String orderId;
    private final String driverId;

    public AssignOrderInOrderAggregateCommand(String assignmentId, String orderId, String driverId) {
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
