package com.epam.javacc.microservices.common.driver.event;

import java.io.Serializable;

public class AssignOrderInDriverAggregateSuccessEvent implements Serializable {

    private static final long serialVersionUID = 2869167202868323416L;
    private String orderId;
    private String driverId;
    private String assignmentId;

    public AssignOrderInDriverAggregateSuccessEvent() {
    }

    public AssignOrderInDriverAggregateSuccessEvent(String orderId, String driverId, String assignmentId) {
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

