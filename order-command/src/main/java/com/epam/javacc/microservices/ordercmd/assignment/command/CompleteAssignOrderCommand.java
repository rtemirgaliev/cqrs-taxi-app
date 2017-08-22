package com.epam.javacc.microservices.ordercmd.assignment.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CompleteAssignOrderCommand {

    @TargetAggregateIdentifier
    private final String assignmentId;

    public CompleteAssignOrderCommand(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
