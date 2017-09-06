package com.epam.javacc.microservices.drivercmd.assignment.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class StartOrderAssignmentCommand {

    @TargetAggregateIdentifier
    private final String assignmentId;
    private final String orderId;
    private final String driverId;

    public StartOrderAssignmentCommand(String assignmentId, String orderId, String driverId) {
        this.assignmentId = (assignmentId == null ? UUID.randomUUID().toString() : assignmentId);
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
