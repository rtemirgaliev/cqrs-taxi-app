package com.epam.javacc.microservices.orderquery.handler;

import com.epam.javacc.microservices.common.order.event.OrderCreatedEvent;
import com.epam.javacc.microservices.common.order.event.OrderStatusChangedEvent;
import com.epam.javacc.microservices.common.order.event.OrderUpdatedEvent;
import com.epam.javacc.microservices.orderquery.domain.Order;
import com.epam.javacc.microservices.orderquery.repository.OrderRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.SequenceNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ProcessingGroup("taxiOrderEvents")
@Component
public class OrderViewEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OrderViewEventHandler.class);

    @Autowired
    private OrderRepository orderRepository;

    @EventHandler
    public void handle(OrderCreatedEvent event) {
        LOG.info("OrderCreatedEvent: [{}] ", event.getOrderId());
        orderRepository.save(new Order(event));
    }

    @EventHandler
    public void handle(OrderUpdatedEvent event) {
        LOG.info("OrderUpdatedEvent: [{}] ", event.getOrderId());
        Order order = orderRepository.findOne(event.getOrderId());
        order.setBusinessKey(event.getBusinessKey());
        order.setAddress(event.getAddress());
        order.setPhone(event.getPhone());
        order.setBusinessKey(event.getBusinessKey());
        order.setStatus(event.getStatus());
        orderRepository.save(order);
    }

    @EventHandler
    public void handle(OrderStatusChangedEvent event) {
        LOG.info("OrderStatusChangedEvent: [{}] ", event.getOrderId());
        Order order = orderRepository.findOne(event.getOrderId());
        order.setStatus(event.getStatus());
        orderRepository.save(order);
    }


}
