package com.epam.javacc.microservices.ordercmd.driver;

import com.epam.javacc.microservices.common.driver.event.DriverCreatedEvent;
import com.epam.javacc.microservices.common.driver.event.DriverStatusChangedEvent;
import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import com.epam.javacc.microservices.ordercmd.driver.command.ChangeDriverStatusCommand;
import com.epam.javacc.microservices.ordercmd.driver.command.CreateDriverCommand;
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

    private static final Logger LOG = LoggerFactory.getLogger(OrderAggregate.class);

    @AggregateIdentifier
    private String driverId;
    private String driverName;
    private DriverStatus driverStatus;
    private String assingnedOrderId;

    public DriverAggregate() {
    }

    @CommandHandler
    public DriverAggregate(CreateDriverCommand command) {
        LOG.debug("Command: 'CreateDriverCommand' received.");
        LOG.debug("Queuing up a new DriverCreatedEvent for driver '{}'", command.getDriverId());
        apply(new DriverCreatedEvent(command.getDriverId(), command.getName(), DriverStatus.ON_VACATION, null));
    }

    @CommandHandler
    public void handle(ChangeDriverStatusCommand command) {
        LOG.debug("Command: 'ChangeDriverStatusCommand' received.");
        apply(new DriverStatusChangedEvent(command.getDriverId(), command.getDriverStatus()));
    }

    @EventSourcingHandler
    public void on(DriverCreatedEvent event) {
        this.driverId = event.getDriverId();
        this.driverName = event.getName();
        this.driverStatus = event.getStatus();
        this.assingnedOrderId = event.getAssignedOrderId();
        LOG.debug("Applied: 'DriverCreatedEvent' [{}]", this.driverId);
    }

    @EventSourcingHandler
    public void on(DriverStatusChangedEvent event) {
        this.driverId = event.getDriverId();
        this.driverStatus = event.getDriverStatus();
        LOG.debug("Applied: 'DriverStatusUpdatedEvent' [{}] -> {}", this.driverId, this.driverStatus);
    }


    public String getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public String getAssingnedOrderId() {
        return assingnedOrderId;
    }
}
