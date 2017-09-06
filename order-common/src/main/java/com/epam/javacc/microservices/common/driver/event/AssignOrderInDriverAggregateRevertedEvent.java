package com.epam.javacc.microservices.common.driver.event;

import java.io.Serializable;

public class AssignOrderInDriverAggregateRevertedEvent implements Serializable {

    private static final long serialVersionUID = -6211700254542250033L;
    private String orderId;
    private String driverId;
    private String assignmentId;

    public AssignOrderInDriverAggregateRevertedEvent() {
    }

    public AssignOrderInDriverAggregateRevertedEvent(String orderId, String driverId, String assignmentId) {
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

