package com.epam.javacc.microservices.common.assignment.event;

import java.io.Serializable;

public class OrderAssignmentCompletedEvent implements Serializable {

    private static final long serialVersionUID = -7466090904901788659L;

    private final String assignmentId;

    public OrderAssignmentCompletedEvent(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
