package com.epam.javacc.microservices.ordercmd.assignment.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CancelAssignOrderCommand {

    @TargetAggregateIdentifier
    private final String assignmentId;

    public CancelAssignOrderCommand(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
