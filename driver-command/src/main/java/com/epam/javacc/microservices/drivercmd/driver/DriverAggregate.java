package com.epam.javacc.microservices.drivercmd.driver;

import com.epam.javacc.microservices.common.driver.event.*;
import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import com.epam.javacc.microservices.drivercmd.assignment.command.AssignOrderInDriverAggregateCommand;
import com.epam.javacc.microservices.drivercmd.assignment.command.RevertAssignOrderInDriverAggregateCommand;
import com.epam.javacc.microservices.drivercmd.driver.command.ChangeDriverStatusCommand;
import com.epam.javacc.microservices.drivercmd.driver.command.CreateDriverCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class DriverAggregate {

    private static final Logger LOG = LoggerFactory.getLogger(DriverAggregate.class);

    @AggregateIdentifier
    private String driverId;
    private String fullName;
    private DriverStatus driverStatus;
    private String assignedOrderId;

    public DriverAggregate() {
    }

    @CommandHandler
    public DriverAggregate(CreateDriverCommand command) {
        LOG.debug("Command: 'CreateDriverCommand' received.");
        apply(new DriverCreatedEvent(command.getDriverId(), command.getFullName(), DriverStatus.ON_VACATION, null));
    }

    @EventSourcingHandler
    public void on(DriverCreatedEvent event) {
        this.driverId = event.getDriverId();
        this.fullName = event.getFullName();
        this.driverStatus = event.getDriverStatus();
        this.assignedOrderId = event.getAssignedOrderId();
        LOG.debug("Applied: 'DriverCreatedEvent' [{}] -> {}, {}, {}", this.driverId, this.fullName, this.driverStatus, this.assignedOrderId);
    }

    @CommandHandler
    public void handle(ChangeDriverStatusCommand command) {
        LOG.debug("Command: 'ChangeDriverStatusCommand' received.");
        apply(new DriverStatusChangedEvent(command.getDriverId(), command.getDriverStatus(), command.getTransactionId()));
    }

    @EventSourcingHandler
    public void on(DriverStatusChangedEvent event) {
        this.driverId = event.getDriverId();
        this.driverStatus = event.getDriverStatus();
        LOG.debug("Applied: 'DriverStatusChangedEvent' [{}] -> {}", this.driverId, this.driverStatus);
    }


    @CommandHandler
    public void handle(AssignOrderInDriverAggregateCommand command) {
        LOG.debug("Command: 'AssignOrderInDriverAggregateCommand' received.");
        if (driverStatus == DriverStatus.EMPTY) {
            apply(new AssignOrderInDriverAggregateSuccessEvent(command.getOrderId(), command.getDriverId(), command.getAssignmentId()));
        } else {
            apply(new AssignOrderInDriverAggregateRejectedEvent(command.getOrderId(), command.getDriverId(), command.getAssignmentId()));
        }
    }

    @EventSourcingHandler
    public void on(AssignOrderInDriverAggregateSuccessEvent event) {
        this.driverStatus = DriverStatus.OCCUPIED;
        this.assignedOrderId = event.getOrderId();
        LOG.debug("Applied: 'AssignOrderInDriverAggregateSuccessEvent' [{}] -> {}, {}", this.driverId, this.driverStatus, this.assignedOrderId);
    }

    @EventSourcingHandler
    public void on(AssignOrderInDriverAggregateRejectedEvent event) {
        LOG.debug("Applied: 'AssignOrderInDriverAggregateRejectedEvent' [{}] -> {}", this.driverId, this.driverStatus);
    }


    @CommandHandler
    public void handle(RevertAssignOrderInDriverAggregateCommand command) {
        LOG.debug("Command: 'RevertAssignOrderInDriverAggregateCommand' received.");
        apply(new AssignOrderInDriverAggregateRejectedEvent(command.getOrderId(), command.getDriverId(), command.getAssignmentId()));
    }


    @EventSourcingHandler
    public void on(AssignOrderInDriverAggregateRevertedEvent event) {
        this.driverStatus = DriverStatus.EMPTY;
        this.assignedOrderId = null;
        LOG.debug("Applied: 'AssignOrderInDriverAggregateRevertedEvent' [{}] -> {}", this.driverId, this.driverStatus);
    }




    public String getDriverId() {
        return driverId;
    }

    public String getFullName() {
        return fullName;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public String getAssignedOrderId() {
        return assignedOrderId;
    }
}
