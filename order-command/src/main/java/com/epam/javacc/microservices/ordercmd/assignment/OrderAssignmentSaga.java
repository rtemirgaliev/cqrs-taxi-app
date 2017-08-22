package com.epam.javacc.microservices.ordercmd.assignment;

import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderRequestedEvent;
import com.epam.javacc.microservices.ordercmd.driver.command.AssignOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderAssignmentSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "assignmentId")
    public void on(AssignOrderRequestedEvent event) {
        commandGateway.send(new AssignOrderCommand(event.getOrderId(), orderId));
    }

}
