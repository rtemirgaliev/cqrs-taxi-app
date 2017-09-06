package com.epam.javacc.microservices.drivercmd.assignment.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CompleteOrderAssignmentCommand {

    @TargetAggregateIdentifier
    private final String assignmentId;

    public CompleteOrderAssignmentCommand(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
