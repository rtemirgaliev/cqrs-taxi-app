package com.epam.javacc.microservices.ordercmd.assignment.event;

public class AssignOrderRequestedEvent {

    private final String assignmentId;
    private final String orderId;
    private final String driverId;

    public AssignOrderRequestedEvent(String assignmentId, String orderId, String driverId) {
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
