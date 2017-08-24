package com.epam.javacc.microservices.ordercmd.assignment;

import com.epam.javacc.microservices.common.driver.event.DriverOrderChangedEvent;
import com.epam.javacc.microservices.common.order.event.OrderStatusChangedEvent;
import com.epam.javacc.microservices.common.order.model.OrderStatus;
import com.epam.javacc.microservices.ordercmd.assignment.command.CompleteAssignOrderCommand;
import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderCompletedEvent;
import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderRequestedEvent;
import com.epam.javacc.microservices.ordercmd.driver.command.ChangeDriverOrderCommand;
import com.epam.javacc.microservices.ordercmd.order.command.ChangeOrderStatusCommand;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderAssignmentSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    private String driverId;

    @StartSaga
    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderRequestedEvent event) {
        this.driverId = event.getDriverId();
        commandGateway.send(
                new ChangeOrderStatusCommand(
                        event.getOrderId(),
                        OrderStatus.ASSIGNED_TO_DRIVER,
                        event.getAssignmentId()
                ),
                LoggingCallback.INSTANCE
        );
    }

    @SagaEventHandler(associationProperty = "transactionId", keyName = "assignmentId")
    public void on(OrderStatusChangedEvent event) {
        commandGateway.send(
                new ChangeDriverOrderCommand(
                        this.driverId,
                        event.getOrderId(),
                        event.getTransactionId()
                ),
                LoggingCallback.INSTANCE
        );
    }

    @SagaEventHandler(associationProperty = "transactionId", keyName = "assignmentId")
    public void on(DriverOrderChangedEvent event) {
        commandGateway.send(
                new CompleteAssignOrderCommand(
                        event.getTransactionId()
                ),
                LoggingCallback.INSTANCE
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderCompletedEvent event) {

    }


}
