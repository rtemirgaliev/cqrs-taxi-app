package com.epam.javacc.microservices.ordercmd.driver;

import com.epam.javacc.microservices.common.driver.event.DriverCreatedEvent;
import com.epam.javacc.microservices.common.driver.event.DriverOrderChangedEvent;
import com.epam.javacc.microservices.common.driver.event.DriverStatusChangedEvent;
import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import com.epam.javacc.microservices.ordercmd.driver.command.ChangeDriverOrderCommand;
import com.epam.javacc.microservices.ordercmd.driver.command.ChangeDriverStatusCommand;
import com.epam.javacc.microservices.ordercmd.driver.command.CreateDriverCommand;
import com.epam.javacc.microservices.ordercmd.driver.exception.WrongDriverStatusException;
import com.epam.javacc.microservices.ordercmd.order.OrderAggregate;
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

    @CommandHandler
    public void handle(ChangeDriverStatusCommand command) {
        LOG.debug("Command: 'ChangeDriverStatusCommand' received.");
        apply(new DriverStatusChangedEvent(command.getDriverId(), command.getDriverStatus(), command.getTransactionId()));
    }

    @CommandHandler
    public void handle(ChangeDriverOrderCommand command) throws WrongDriverStatusException {
        LOG.debug("Command: 'ChangeDriverOrderCommand' received.");
        //TODO check that driver status is empty
        if (DriverStatus.EMPTY.equals(this.driverStatus)) {
            apply(new DriverOrderChangedEvent(command.getDriverId(), command.getAssignedOrderId(), command.getTransactionId()));
            apply(new DriverStatusChangedEvent(command.getDriverId(), DriverStatus.OCCUPIED, command.getTransactionId()));
        } else {
            throw new WrongDriverStatusException("The order can be assigned to driver with status EMPTY");
        }
    }

    @EventSourcingHandler
    public void on(DriverCreatedEvent event) {
        this.driverId = event.getDriverId();
        this.fullName = event.getName();
        this.driverStatus = event.getStatus();
        this.assignedOrderId = event.getAssignedOrderId();
        LOG.debug("Applied: 'DriverCreatedEvent' [{}] -> {}, {}, {}", this.driverId, this.fullName, this.driverStatus, this.assignedOrderId);
    }

    @EventSourcingHandler
    public void on(DriverOrderChangedEvent event) {
        this.driverId = event.getDriverId();
        this.assignedOrderId = event.getAssignedOrderId();
        LOG.debug("Applied: 'DriverOrderChangedEvent' [{}] -> {}", this.driverId, this.assignedOrderId);
    }

    @EventSourcingHandler
    public void on(DriverStatusChangedEvent event) {
        this.driverId = event.getDriverId();
        this.driverStatus = event.getDriverStatus();
        LOG.debug("Applied: 'DriverStatusChangedEvent' [{}] -> {}", this.driverId, this.driverStatus);
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
