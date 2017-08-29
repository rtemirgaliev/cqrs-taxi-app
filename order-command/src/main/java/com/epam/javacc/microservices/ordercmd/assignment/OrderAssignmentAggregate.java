package com.epam.javacc.microservices.ordercmd.assignment;

import com.epam.javacc.microservices.ordercmd.assignment.command.RejectOrderAssignmentCommand;
import com.epam.javacc.microservices.ordercmd.assignment.command.CompleteOrderAssignmentCommand;
import com.epam.javacc.microservices.ordercmd.assignment.command.StartOrderAssignmentCommand;
import com.epam.javacc.microservices.ordercmd.assignment.event.OrderAssignmentCompletedEvent;
import com.epam.javacc.microservices.ordercmd.assignment.event.OrderAssignmentRejectedEvent;
import com.epam.javacc.microservices.ordercmd.assignment.event.OrderAssignmentStartedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class OrderAssignmentAggregate {

    private static final Logger LOG = LoggerFactory.getLogger(OrderAssignmentAggregate.class);

    private enum AssignmentStatus {STARTED, FAILED, COMPLETED}

    @AggregateIdentifier
    private String assignmentId;
    private String orderId;
    private String driverId;

    private AssignmentStatus status;


    public OrderAssignmentAggregate() {
    }

    @CommandHandler
    public OrderAssignmentAggregate(StartOrderAssignmentCommand cmd) {
        LOG.debug("Command: 'StartOrderAssignmentCommand' received [{}]: {} to {}", cmd.getAssignmentId(), cmd.getOrderId(), cmd.getDriverId());
        apply(new OrderAssignmentStartedEvent(cmd.getAssignmentId(), cmd.getOrderId(), cmd.getDriverId()));
    }

    @CommandHandler
    public void handle(CompleteOrderAssignmentCommand cmd) {
        LOG.debug("Command: 'CompleteOrderAssignmentCommand' received.");
        apply(new OrderAssignmentCompletedEvent(cmd.getAssignmentId()));
    }

    @CommandHandler
    public void handle(RejectOrderAssignmentCommand cmd) {
        LOG.debug("Command: 'RejectOrderAssignmentCommand' received.");
        apply(new OrderAssignmentRejectedEvent(cmd.getAssignmentId()));
    }


    @EventSourcingHandler
    public void on(OrderAssignmentStartedEvent event) {
        this.assignmentId = event.getAssignmentId();
        this.orderId = event.getOrderId();
        this.driverId = event.getDriverId();
        this.status = AssignmentStatus.STARTED;
        LOG.debug("Applied: 'OrderAssignmentStartedEvent' [{}]", this.assignmentId);
    }

    @EventSourcingHandler
    public void on(OrderAssignmentCompletedEvent event) {
        this.status = AssignmentStatus.COMPLETED;
        LOG.debug("Applied: 'OrderAssignmentCompletedEvent' [{}]", this.assignmentId);
    }

    @EventSourcingHandler
    public void on(OrderAssignmentRejectedEvent event) {
        this.status = AssignmentStatus.FAILED;
        LOG.debug("Applied: 'OrderAssignmentRejectedEvent' [{}]", this.assignmentId);
        //TODO Raise Exception here
    }

}
