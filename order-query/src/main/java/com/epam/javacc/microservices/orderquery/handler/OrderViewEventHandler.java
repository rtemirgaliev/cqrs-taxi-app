package com.epam.javacc.microservices.orderquery.handler;

import com.epam.javacc.microservices.common.order.event.AssignOrderInOrderAggregateSuccessEvent;
import com.epam.javacc.microservices.common.order.event.OrderCreatedEvent;
import com.epam.javacc.microservices.common.order.event.OrderDeletedEvent;
import com.epam.javacc.microservices.common.order.event.OrderUpdatedEvent;
import com.epam.javacc.microservices.common.order.model.OrderStatus;
import com.epam.javacc.microservices.orderquery.domain.Order;
import com.epam.javacc.microservices.orderquery.repository.OrderRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@ProcessingGroup("taxiExchange")
@Component
public class OrderViewEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OrderViewEventHandler.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventHandler
    public void handle(OrderCreatedEvent event) {
        LOG.info("OrderCreatedEvent: [{}] ", event.getOrderId());
        orderRepository.save(new Order(event));
        broadcastUpdates();
    }

    @EventHandler
    public void handle(OrderDeletedEvent event) {
        LOG.info("OrderDeletedEvent: [{}] ", event.getOrderId());
        orderRepository.delete(event.getOrderId());
        broadcastUpdates();
    }

    @EventHandler
    public void handle(OrderUpdatedEvent event) {
        LOG.info("OrderUpdatedEvent: [{}] ", event.getOrderId());
        Order order = orderRepository.findOne(event.getOrderId());
        order.setAddress(event.getAddress());
        order.setPhone(event.getPhone());
        order.setStatus(event.getStatus());
        orderRepository.save(order);
        broadcastUpdates();
    }

    @EventHandler
    public void handle(AssignOrderInOrderAggregateSuccessEvent event) {
        LOG.info("AssignOrderInOrderAggregateSuccessEvent: [{}] ", event.getOrderId());
        Order order = orderRepository.findOne(event.getOrderId());
        order.setStatus(OrderStatus.ASSIGNED_TO_DRIVER);
        orderRepository.save(order);
        broadcastUpdates();
    }

    private void broadcastUpdates() {
        Iterable<Order> orders = orderRepository.findAll();
        messagingTemplate.convertAndSend("/topic/order-list.updates", orders);
    }


}
