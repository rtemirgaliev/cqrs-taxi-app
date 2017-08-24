package com.epam.javacc.microservices.ordercmd.assignment.event;

import java.io.Serializable;

public class AssignOrderCompletedEvent implements Serializable {

    private static final long serialVersionUID = -7466090904901788659L;

    private final String assignmentId;

    public AssignOrderCompletedEvent(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

}
