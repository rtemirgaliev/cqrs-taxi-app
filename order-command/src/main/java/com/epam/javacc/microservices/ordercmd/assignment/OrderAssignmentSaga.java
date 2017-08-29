package com.epam.javacc.microservices.ordercmd.assignment;

import com.epam.javacc.microservices.common.driver.event.DriverOrderChangedEvent;
import com.epam.javacc.microservices.common.order.event.OrderStatusChangedEvent;
import com.epam.javacc.microservices.common.order.model.OrderStatus;
import com.epam.javacc.microservices.ordercmd.assignment.command.CompleteOrderAssignmentCommand;
import com.epam.javacc.microservices.ordercmd.assignment.event.OrderAssignmentCompletedEvent;
import com.epam.javacc.microservices.ordercmd.assignment.event.OrderAssignmentStartedEvent;
import com.epam.javacc.microservices.ordercmd.driver.command.ChangeDriverOrderCommand;
import com.epam.javacc.microservices.ordercmd.order.command.ChangeOrderStatusCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderAssignmentSaga {

    private static final Logger LOG = LoggerFactory.getLogger(OrderAssignmentSaga.class);

    @Autowired
    private transient CommandGateway commandGateway;

    private String orderId;
    private String driverId;

    @StartSaga
    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(OrderAssignmentStartedEvent event) {
        LOG.debug("Applied: 'OrderAssignmentStartedEvent' [{}]", event.getAssignmentId());
        this.orderId = event.getOrderId();
        this.driverId = event.getDriverId();
        commandGateway.send(new ChangeOrderStatusCommand(event.getOrderId(), OrderStatus.ASSIGNED_TO_DRIVER, event.getAssignmentId()));
    }

    @SagaEventHandler(associationProperty = "transactionId", keyName = "assignmentId")
    public void on(OrderStatusChangedEvent event) {
        LOG.debug("Applied: 'OrderStatusChangedEvent'", event.getTransactionId());
        commandGateway.send(new ChangeDriverOrderCommand(this.driverId, event.getOrderId(), event.getTransactionId()));
    }

    @SagaEventHandler(associationProperty = "transactionId", keyName = "assignmentId")
    public void on(DriverOrderChangedEvent event) {
        LOG.debug("Applied: 'DriverOrderChangedEvent'", event.getTransactionId());
        commandGateway.send(new CompleteOrderAssignmentCommand(event.getTransactionId()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(OrderAssignmentCompletedEvent event) {
        LOG.debug("Applied: 'OrderAssignmentCompletedEvent' [{}]", event.getAssignmentId());
    }


}
