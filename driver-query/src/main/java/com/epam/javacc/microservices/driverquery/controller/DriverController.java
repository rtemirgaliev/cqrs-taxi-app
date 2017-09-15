package com.epam.javacc.microservices.driverquery.controller;

import com.epam.javacc.microservices.driverquery.domain.Driver;
import com.epam.javacc.microservices.driverquery.repository.DriverRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private DriverRepository repository;

    public DriverController(DriverRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Driver> all() {
        return repository.findAll();
    }



}
