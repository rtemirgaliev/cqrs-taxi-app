package com.epam.javacc.microservices.ordercmd.assignment.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class RejectOrderAssignmentCommand {

    @TargetAggregateIdentifier
    private final String assignmentId;

    public RejectOrderAssignmentCommand(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
