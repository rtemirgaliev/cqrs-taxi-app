package com.epam.javacc.microservices.common.driver.event;

import java.io.Serializable;

public class AssignOrderInDriverAggregateRejectedEvent implements Serializable {

    private static final long serialVersionUID = 6824138643162831269L;
    private String orderId;
    private String driverId;
    private String assignmentId;

    public AssignOrderInDriverAggregateRejectedEvent() {
    }

    public AssignOrderInDriverAggregateRejectedEvent(String orderId, String driverId, String assignmentId) {
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
