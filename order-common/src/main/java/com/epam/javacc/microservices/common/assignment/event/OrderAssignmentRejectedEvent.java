package com.epam.javacc.microservices.common.assignment.event;

import java.io.Serializable;

public class OrderAssignmentRejectedEvent implements Serializable{

    private static final long serialVersionUID = -6279072922619997448L;

    private final String assignmentId;

    public OrderAssignmentRejectedEvent(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
