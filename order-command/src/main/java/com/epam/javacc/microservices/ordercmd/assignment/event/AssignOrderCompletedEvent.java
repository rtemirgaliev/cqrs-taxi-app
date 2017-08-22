package com.epam.javacc.microservices.ordercmd.assignment.event;

public class AssignOrderCompletedEvent {

    private final String assignmentId;

    public AssignOrderCompletedEvent(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
