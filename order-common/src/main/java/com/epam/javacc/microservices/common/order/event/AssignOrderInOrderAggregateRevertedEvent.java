package com.epam.javacc.microservices.common.order.event;

import java.io.Serializable;

public class AssignOrderInOrderAggregateRevertedEvent implements Serializable {

    private static final long serialVersionUID = -7688078404240321296L;
    private String orderId;
    private String driverId;
    private String assignmentId;

    public AssignOrderInOrderAggregateRevertedEvent() {
    }

    public AssignOrderInOrderAggregateRevertedEvent(String orderId, String driverId, String assignmentId) {
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
