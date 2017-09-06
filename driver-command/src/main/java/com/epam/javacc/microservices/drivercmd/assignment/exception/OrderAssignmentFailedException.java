package com.epam.javacc.microservices.drivercmd.assignment.exception;

public class OrderAssignmentFailedException extends Exception {
    public OrderAssignmentFailedException() {
    }

    public OrderAssignmentFailedException(String message) {
        super(message);
    }
}
