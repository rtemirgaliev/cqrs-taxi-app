package com.epam.javacc.microservices.driverquery.handler;

import com.epam.javacc.microservices.common.driver.event.AssignOrderInDriverAggregateSuccessEvent;
import com.epam.javacc.microservices.common.driver.event.DriverCreatedEvent;
import com.epam.javacc.microservices.common.driver.event.DriverStatusChangedEvent;
import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import com.epam.javacc.microservices.driverquery.domain.Driver;
import com.epam.javacc.microservices.driverquery.repository.DriverRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@ProcessingGroup("taxiExchange")
@Component
public class DriverViewEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DriverViewEventHandler.class);

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventHandler
    public void handle(DriverCreatedEvent event) {
        LOG.info("DriverCreatedEvent: [{}] ", event.getDriverId());
        driverRepository.save(new Driver(event));
        broadcastUpdates();
    }

    @EventHandler
    public void handle(DriverStatusChangedEvent event) {
        LOG.info("DriverStatusChangedEvent: [{}] ", event.getDriverId());
        Driver driver = driverRepository.findOne(event.getDriverId());
        driver.setDriverStatus(event.getDriverStatus());
        driverRepository.save(driver);
        broadcastUpdates();
    }

    @EventHandler
    public void handle(AssignOrderInDriverAggregateSuccessEvent event) {
        LOG.info("AssignOrderInDriverAggregateSuccessEvent: [{}] ", event.getDriverId());
        Driver driver = driverRepository.findOne(event.getDriverId());
        driver.setDriverStatus(DriverStatus.OCCUPIED);
        driver.setAssignedOrderId(event.getOrderId());
        driverRepository.save(driver);
        broadcastUpdates();
    }
    private void broadcastUpdates() {
        Iterable<Driver> drivers = driverRepository.findAll();
        messagingTemplate.convertAndSend("/topic/driver-list.updates", drivers);
    }

}
