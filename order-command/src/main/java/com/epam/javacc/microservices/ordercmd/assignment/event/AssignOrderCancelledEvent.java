package com.epam.javacc.microservices.ordercmd.assignment.event;

import java.io.Serializable;

public class AssignOrderCancelledEvent implements Serializable{

    private static final long serialVersionUID = -6279072922619997448L;

    private final String assignmentId;

    public AssignOrderCancelledEvent(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
