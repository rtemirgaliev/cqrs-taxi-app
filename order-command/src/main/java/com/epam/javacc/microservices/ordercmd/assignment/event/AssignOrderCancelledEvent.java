package com.epam.javacc.microservices.ordercmd.assignment.event;

public class AssignOrderCancelledEvent {

    private final String assignmentId;

    public AssignOrderCancelledEvent(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
