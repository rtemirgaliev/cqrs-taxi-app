package com.epam.javacc.microservices.drivercmd.assignment;

import com.epam.javacc.microservices.common.assignment.event.RevertAssignOrderInOrderAggregateCommandEvent;
import com.epam.javacc.microservices.common.driver.event.AssignOrderInDriverAggregateRejectedEvent;
import com.epam.javacc.microservices.common.driver.event.AssignOrderInDriverAggregateRevertedEvent;
import com.epam.javacc.microservices.common.driver.event.AssignOrderInDriverAggregateSuccessEvent;
import com.epam.javacc.microservices.common.order.event.AssignOrderInOrderAggregateRejectedEvent;
import com.epam.javacc.microservices.common.order.event.AssignOrderInOrderAggregateRevertedEvent;
import com.epam.javacc.microservices.common.order.event.AssignOrderInOrderAggregateSuccessEvent;
import com.epam.javacc.microservices.drivercmd.assignment.command.*;
import com.epam.javacc.microservices.common.assignment.event.OrderAssignmentStartedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;
import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

//@Saga
public class OrderAssignmentSaga {

    private static final Logger LOG = LoggerFactory.getLogger(OrderAssignmentSaga.class);

//    private enum OrderStatusChange {STARTED, SUCCESS, REJECTED}
//    private enum DriverOrderChange {STARTED, SUCCESS, REJECTED}

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired transient EventBus eventBus;


    private String orderId;
    private String driverId;
    private String assignmentId;
    private boolean orderChangedSuccessfully;
    private boolean driverChangedSuccessfully;
//    private boolean driverAggregateRejectedAssignment;
//    private boolean orderAggregateRejectedAssignment;
//    private boolean driverAggregateRevertedAssignment;
//    private boolean orderAggregateRevertedAssignment;


    @StartSaga
    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(OrderAssignmentStartedEvent event) {
        LOG.debug("Applied: 'OrderAssignmentStartedEvent' [{}]", event.getAssignmentId());
        this.orderId = event.getOrderId();
        this.driverId = event.getDriverId();
        this.assignmentId = event.getAssignmentId();
        //TODO remove
//        orderStatusChange = OrderStatusChange.STARTED;
//        driverOrderChange = DriverOrderChange.STARTED;
//        commandGateway.send(new AssignOrderInOrderAggregateCommand(event.getAssignmentId(), event.getOrderId(), event.getDriverId()));
        commandGateway.send(new AssignOrderInDriverAggregateCommand(event.getAssignmentId(), event.getOrderId(), event.getDriverId()));
    }


    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderInOrderAggregateSuccessEvent event) {
        LOG.debug("Applied: 'AssignOrderInOrderAggregateSuccessEvent'", event.getAssignmentId());
        orderChangedSuccessfully = true;
        if (driverChangedSuccessfully) {
            commandGateway.send(new CompleteOrderAssignmentCommand(event.getAssignmentId()));
            end();
        }
    }

    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderInDriverAggregateSuccessEvent event) {
        LOG.debug("Applied: 'AssignOrderInDriverAggregateSuccessEvent'", event.getAssignmentId());
        driverChangedSuccessfully = true;
        if (orderChangedSuccessfully) {
            commandGateway.send(new CompleteOrderAssignmentCommand(event.getAssignmentId()));
            end();
        }
    }

    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderInDriverAggregateRevertedEvent event) {
        LOG.debug("Applied: 'AssignOrderInDriverAggregateRevertedEvent'", event.getAssignmentId());
        commandGateway.send(new RejectOrderAssignmentCommand(event.getAssignmentId()));
        end();

    }

    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderInOrderAggregateRevertedEvent event) {
        LOG.debug("Applied: 'AssignOrderInOrderAggregateRevertedEvent'", event.getAssignmentId());
        commandGateway.send(new RejectOrderAssignmentCommand(event.getAssignmentId()));
        end();
    }


    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderInOrderAggregateRejectedEvent event) {
        LOG.debug("Applied: 'AssignOrderInOrderAggregateRejectedEvent'", event.getAssignmentId());

        commandGateway.send(new RevertAssignOrderInDriverAggregateCommand(event.getAssignmentId(), event.getOrderId(), event.getDriverId()));

    }

    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderInDriverAggregateRejectedEvent event) {
        LOG.debug("Applied: 'AssignOrderInDriverAggregateRejectedEvent'", event.getAssignmentId());

        eventBus.publish(asEventMessage(
                new RevertAssignOrderInOrderAggregateCommandEvent(event.getAssignmentId(), event.getOrderId(), event.getDriverId())));
    }

}
