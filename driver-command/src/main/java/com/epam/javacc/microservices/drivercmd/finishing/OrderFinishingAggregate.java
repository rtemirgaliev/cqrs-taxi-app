package com.epam.javacc.microservices.drivercmd.finishing;

import com.epam.javacc.microservices.common.assignment.event.OrderAssignmentCompletedEvent;
import com.epam.javacc.microservices.common.assignment.event.OrderAssignmentRejectedEvent;
import com.epam.javacc.microservices.common.assignment.event.OrderAssignmentStartedEvent;
import com.epam.javacc.microservices.drivercmd.assignment.command.CompleteOrderAssignmentCommand;
import com.epam.javacc.microservices.drivercmd.assignment.command.RejectOrderAssignmentCommand;
import com.epam.javacc.microservices.drivercmd.assignment.command.StartOrderAssignmentCommand;
import com.epam.javacc.microservices.drivercmd.assignment.exception.OrderAssignmentFailedException;
import com.epam.javacc.microservices.drivercmd.finishing.command.StartOrderFinishingCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class OrderFinishingAggregate {

    private static final Logger LOG = LoggerFactory.getLogger(OrderFinishingAggregate.class);

//    private enum AssignmentStatus {STARTED, FAILED, COMPLETED}

    @AggregateIdentifier
    private String finishingId;
    private String driverId;
    private String orderId;

//    private AssignmentStatus status;


    public OrderFinishingAggregate() {
    }

    @CommandHandler
    public OrderFinishingAggregate(StartOrderFinishingCommand cmd) {
        LOG.debug("Command: 'StartOrderFinishingCommand' received [{}]", cmd.getFinishingId());
        apply(new OrderFinishingStartedEvent(cmd.getFinishingId(), cmd.getDriverId()));
    }

    @EventSourcingHandler
    public void on(OrderFinishingStartedEvent event) {
        this.finishingId = event.getFinishingId();
//        this.orderId = event.getOrderId();
//        this.driverId = event.getDriverId();
//        this.status = AssignmentStatus.STARTED;
        LOG.debug("Applied: 'OrderFinishingStartedEvent' [{}]", this.finishingId);
    }

    @CommandHandler
    public void handle(CompleteOrderAssignmentCommand cmd) {
        LOG.debug("Command: 'CompleteOrderAssignmentCommand' received.");
        apply(new OrderAssignmentCompletedEvent(cmd.getAssignmentId()));
    }

    @EventSourcingHandler
    public void on(OrderAssignmentCompletedEvent event) {
        this.status = AssignmentStatus.COMPLETED;
        LOG.debug("Applied: 'OrderAssignmentCompletedEvent' [{}]", this.assignmentId);
    }


    @CommandHandler
    public void handle(RejectOrderAssignmentCommand cmd) {
        LOG.debug("Command: 'RejectOrderAssignmentCommand' received.");
        apply(new OrderAssignmentRejectedEvent(cmd.getAssignmentId()));
    }

    @EventSourcingHandler
    public void on(OrderAssignmentRejectedEvent event) throws OrderAssignmentFailedException {
        this.status = AssignmentStatus.FAILED;
        LOG.debug("Applied: 'OrderAssignmentRejectedEvent' [{}]", this.assignmentId);

//        throw new OrderAssignmentFailedException("Could not assign order because the order has already been assigned or taxi occupied ");

    }

}
