package com.epam.javacc.microservices.ordercmd.order;


import com.epam.javacc.microservices.common.order.event.OrderCreatedEvent;
import com.epam.javacc.microservices.common.order.event.OrderStatusChangedEvent;
import com.epam.javacc.microservices.common.order.event.OrderUpdatedEvent;
import com.epam.javacc.microservices.common.order.model.OrderStatus;
import com.epam.javacc.microservices.ordercmd.order.command.ChangeOrderStatusCommand;
import com.epam.javacc.microservices.ordercmd.order.command.CreateOrderCommand;
import com.epam.javacc.microservices.ordercmd.order.command.UpdateOrderCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Aggregate is an entity or a group of related entities that should be maintained in a consistent state
 *
 * @author Rinat Temirgaliev
 *
 */
@Aggregate
public class OrderAggregate {

    private static final Logger LOG = LoggerFactory.getLogger(OrderAggregate.class);

    /**
     * Aggregates managed by Axon must have a unique identifier. It is used by the Even Sourcing mechanism
     * to store and locate all the events in the events repository that are related to this instance of the aggregate
     */
    @AggregateIdentifier
    private String orderId;
    private String businessKey;
    private String phone;
    private String address;
    private OrderStatus status;

    public OrderAggregate() {
    }

    /**
     * This constructor is marked as a 'CommandHandler' for the CreateOrderCommand. This
     * command is used to construct new instances of the Aggregate.
     * The 'apply' method does two things:
     * -it calls method annotated with @EventSourcingHandler: on(OrderCreatedEvent event)
     * -it also propagates the event to any other registered 'Event Listeners', who may take further action
     * @param command
     */
    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        LOG.debug("Command: 'CreateOrderCommand' received.");
        apply(new OrderCreatedEvent(command.getOrderId(), command.getBusinessKey(),
                command.getPhone(), command.getAddress(), command.getStatus()));
    }

    @CommandHandler
    public void handle(UpdateOrderCommand command) {
        LOG.debug("Command: 'UpdateOrderCommand' received.");
        apply(new OrderUpdatedEvent(command.getOrderId(), command.getBusinessKey(),
                command.getPhone(), command.getAddress(), command.getStatus()));
    }

    @CommandHandler
    public void handle(ChangeOrderStatusCommand command) {
        LOG.debug("Command: 'ChangeOrdersStatusCommand' received.");
        apply(new OrderStatusChangedEvent(command.getOrderId(), command.getStatus(), command.getTransactionId()));
    }

    /**
     * This method is marked as an EventSourcingHandler and is therefore used by the Axon
     * framework to handle events of the specified type (OrderCreatedEvent). The
     * OrderCreatedEvent can be raised either by the constructor during
     * OrderAggregate(CreateOrderCommand) or by the Repository when 're-loading' the
     * aggregate.
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.businessKey = event.getBusinessKey();
        this.phone = event.getPhone();
        this.address = event.getAddress();
        this.status = event.getStatus();
        LOG.debug("Applied: 'OrderCreatedEvent' [{}] -> {}, {}, {}", this.orderId, this.phone, this.address, this.status);
    }

    @EventSourcingHandler
    public void on(OrderUpdatedEvent event) {
        this.businessKey = event.getBusinessKey();
        this.phone = event.getPhone();
        this.address = event.getAddress();
        this.status = event.getStatus();
        LOG.debug("Applied: 'OrderUpdatedEvent' [{}] -> {}, {}, {}", this.orderId, this.phone, this.address, this.status);
    }

    @EventSourcingHandler
    public void on(OrderStatusChangedEvent event) {
        this.status = event.getStatus();
        LOG.debug("Applied: 'OrderStatusChangedEvent' [{}] -> {}", this.orderId, this.getStatus());
    }


    public String getOrderId() {
        return orderId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
