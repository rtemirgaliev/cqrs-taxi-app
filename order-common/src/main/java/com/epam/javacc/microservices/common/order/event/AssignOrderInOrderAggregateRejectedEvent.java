package com.epam.javacc.microservices.common.order.event;

import java.io.Serializable;

public class AssignOrderInOrderAggregateRejectedEvent implements Serializable {

    private static final long serialVersionUID = -8302676075637965237L;
    private String orderId;
    private String driverId;
    private String assignmentId;

    public AssignOrderInOrderAggregateRejectedEvent() {
    }

    public AssignOrderInOrderAggregateRejectedEvent(String orderId, String driverId, String assignmentId) {
        this.orderId = orderId;
        this.driverId = driverId;
        this.assignmentId = assignmentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }
}
