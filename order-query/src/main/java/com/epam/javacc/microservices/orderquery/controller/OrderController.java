package com.epam.javacc.microservices.orderquery.controller;

import com.epam.javacc.microservices.orderquery.domain.Order;
import com.epam.javacc.microservices.orderquery.repository.OrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Order> all() {
        return repository.findAll();
    }



}
