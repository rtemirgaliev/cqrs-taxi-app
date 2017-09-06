package com.epam.javacc.microservices.common.order.event;

import java.io.Serializable;

public class AssignOrderInOrderAggregateSuccessEvent implements Serializable {

    private static final long serialVersionUID = 7239310732236999695L;
    private String orderId;
    private String driverId;
    private String assignmentId;

    public AssignOrderInOrderAggregateSuccessEvent() {
    }

    public AssignOrderInOrderAggregateSuccessEvent(String orderId, String driverId, String assignmentId) {
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
