package com.epam.javacc.microservices.orderquery.controller;

import com.epam.javacc.microservices.orderquery.domain.Order;
import com.epam.javacc.microservices.orderquery.repository.OrderRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/stomp/order")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @SubscribeMapping
    public Iterable<Order> all() {
        return orderRepository.findAll();
    }

}
