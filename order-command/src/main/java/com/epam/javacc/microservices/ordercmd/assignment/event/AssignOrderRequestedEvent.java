package com.epam.javacc.microservices.ordercmd.assignment.event;

import java.io.Serializable;

public class AssignOrderRequestedEvent implements Serializable {

    private static final long serialVersionUID = -362818361432688137L;

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
