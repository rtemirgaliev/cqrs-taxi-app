package com.epam.javacc.microservices.drivercmd.assignment.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class RevertAssignOrderInDriverAggregateCommand {

    private final String assignmentId;
    private final String orderId;
    @TargetAggregateIdentifier
    private final String driverId;

    public RevertAssignOrderInDriverAggregateCommand(String assignmentId, String orderId, String driverId) {
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
