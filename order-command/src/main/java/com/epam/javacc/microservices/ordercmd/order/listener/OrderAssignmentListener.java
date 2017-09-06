package com.epam.javacc.microservices.ordercmd.order.listener;

import com.epam.javacc.microservices.common.assignment.event.OrderAssignmentStartedEvent;
import com.epam.javacc.microservices.common.assignment.event.RevertAssignOrderInOrderAggregateCommandEvent;
import com.epam.javacc.microservices.common.driver.event.AssignOrderInDriverAggregateRejectedEvent;
import com.epam.javacc.microservices.ordercmd.order.command.AssignOrderInOrderAggregateCommand;
import com.epam.javacc.microservices.ordercmd.order.command.RevertAssignOrderInOrderAggregateCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ProcessingGroup("taxiExchange")
@Component
public class OrderAssignmentListener {

    private static final Logger LOG = LoggerFactory.getLogger(OrderAssignmentListener.class);

    @Autowired
    private transient CommandGateway commandGateway;

    @EventHandler
    private void handle(OrderAssignmentStartedEvent event) {
       commandGateway.send(new AssignOrderInOrderAggregateCommand(event.getAssignmentId(), event.getOrderId(), event.getDriverId()));
        LOG.debug("Applied: 'OrderAssignmentStartedEvent' [{}]", event.getAssignmentId());
    }

    @EventHandler
    private  void handle(RevertAssignOrderInOrderAggregateCommandEvent event) {
        commandGateway.send(new RevertAssignOrderInOrderAggregateCommand(event.getAssignmentId(), event.getOrderId(), event.getDriverId()));
        LOG.debug("Applied: 'RevertAssignOrderInOrderAggregateCommandEvent' [{}]", event.getAssignmentId());
    }

}
