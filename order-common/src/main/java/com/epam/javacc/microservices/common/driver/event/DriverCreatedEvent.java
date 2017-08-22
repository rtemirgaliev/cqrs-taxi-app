package com.epam.javacc.microservices.common.driver.event;

import com.epam.javacc.microservices.common.driver.model.DriverStatus;

import java.io.Serializable;

public class DriverCreatedEvent implements Serializable {

    private static final long serialVersionUID = 7534361014002829485L;

    private String driverId;
    private String name;
    private DriverStatus status;
    private String assignedOrderId;

    public DriverCreatedEvent() {
    }

    public DriverCreatedEvent(String driverId, String name, DriverStatus status, String assingnedOrderId) {
        this.driverId = driverId;
        this.name = name;
        this.status = status;
        this.assignedOrderId = assingnedOrderId;
    }

    public String getDriverId() {
        return driverId;
    }
    public String getName() {
        return name;
    }
    public DriverStatus getStatus() {
        return status;
    }
    public String getAssignedOrderId() {
        return assignedOrderId;
    }
}
