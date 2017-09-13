package com.epam.javacc.microservices.ordercmd.order;


import com.epam.javacc.microservices.common.order.event.*;
import com.epam.javacc.microservices.common.order.model.OrderStatus;
import com.epam.javacc.microservices.ordercmd.order.command.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

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
    private String phone;
    private String address;
    private OrderStatus orderStatus;

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
        apply(new OrderCreatedEvent(command.getOrderId(), command.getPhone(), command.getAddress(), command.getStatus()));
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
        this.phone = event.getPhone();
        this.address = event.getAddress();
        this.orderStatus = event.getStatus();
        LOG.debug("Applied: 'OrderCreatedEvent' [{}] -> {}, {}, {}", this.orderId, this.phone, this.address, this.orderStatus);
    }

    @CommandHandler
    public void handle(DeleteOrderCommand command) {
        LOG.debug("Command: 'DeleteOrderCommand' received.");
        apply(new OrderDeletedEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderDeletedEvent event) {
        markDeleted();
        LOG.debug("Applied: 'OrderDeletedEvent' [{}] -> {}", this.orderId);
    }

    @CommandHandler
    public void handle(UpdateOrderCommand command) {
        LOG.debug("Command: 'UpdateOrderCommand' received.");
        apply(new OrderUpdatedEvent(command.getOrderId(), command.getPhone(), command.getAddress(), command.getStatus()));
    }

    @EventSourcingHandler
    public void on(OrderUpdatedEvent event) {
        this.phone = event.getPhone();
        this.address = event.getAddress();
        this.orderStatus = event.getStatus();
        LOG.debug("Applied: 'OrderUpdatedEvent' [{}] -> {}, {}, {}", this.orderId, this.phone, this.address, this.orderStatus);
    }


    @CommandHandler
    public void handle(AssignOrderInOrderAggregateCommand command) {
        LOG.debug("Command: 'AssignOrderInOrderAggregateCommand' received.");
        if (orderStatus == OrderStatus.NOT_ASSIGNED) {
            apply(new AssignOrderInOrderAggregateSuccessEvent(command.getOrderId(), command.getDriverId(), command.getAssignmentId()));
        } else {
            apply(new AssignOrderInOrderAggregateRejectedEvent(command.getOrderId(), command.getDriverId(), command.getAssignmentId()));
        }
    }

    @EventSourcingHandler
    public void on(AssignOrderInOrderAggregateSuccessEvent event) {
        this.orderStatus = OrderStatus.ASSIGNED_TO_DRIVER;
        LOG.debug("Applied: 'AssignOrderInOrderAggregateSuccessEvent' [{}] -> {}", this.orderId, this.orderStatus);
    }

    @EventSourcingHandler
    public void on(AssignOrderInOrderAggregateRejectedEvent event) {
        LOG.debug("Applied: 'AssignOrderInOrderAggregateRejectedEvent' [{}] -> {}", this.orderId, this.orderStatus);
    }


    @CommandHandler
    public void handle(RevertAssignOrderInOrderAggregateCommand command) {
        LOG.debug("Command: 'RevertAssignOrderInOrderAggregateCommand' received.");
        apply(new AssignOrderInOrderAggregateRevertedEvent(command.getOrderId(), command.getDriverId(), command.getAssignmentId()));
    }

    @EventSourcingHandler
    public void on(AssignOrderInOrderAggregateRevertedEvent event) {
        this.orderStatus = OrderStatus.NOT_ASSIGNED;
        LOG.debug("Applied: 'AssignOrderInOrderAggregateRevertedEvent' [{}] -> {}", this.orderId, this.orderStatus);
    }


    public String getOrderId() {
        return orderId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
