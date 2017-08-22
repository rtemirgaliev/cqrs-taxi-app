package com.epam.javacc.microservices.ordercmd.assignment;

import com.epam.javacc.microservices.ordercmd.assignment.command.CancelAssignOrderCommand;
import com.epam.javacc.microservices.ordercmd.assignment.command.CompleteAssignOrderCommand;
import com.epam.javacc.microservices.ordercmd.assignment.command.RequestAssignOrderCommand;
import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderCancelledEvent;
import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderCompletedEvent;
import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderRequestedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

@Aggregate
public class OrderAssignmentAggregate {

    @AggregateIdentifier
    private String assignmentId;

    public OrderAssignmentAggregate() {
    }

    @CommandHandler
    public OrderAssignmentAggregate(RequestAssignOrderCommand cmd) {
        apply(new AssignOrderRequestedEvent(cmd.getAssignmentId(), cmd.getOrderId(), cmd.getDriverId()));
    }

    @CommandHandler
    public void handle(CompleteAssignOrderCommand cmd) {
        apply(new AssignOrderCompletedEvent(cmd.getAssignmentId()));
    }

    @CommandHandler
    public void handle(CancelAssignOrderCommand cmd) {
        apply(new AssignOrderCancelledEvent(cmd.getAssignmentId()));
    }

    @EventSourcingHandler
    public void on(AssignOrderRequestedEvent event) {
        this.assignmentId = event.getAssignmentId();
    }

    @EventSourcingHandler
    public void on(AssignOrderCompletedEvent event) {
        markDeleted();
    }
}
