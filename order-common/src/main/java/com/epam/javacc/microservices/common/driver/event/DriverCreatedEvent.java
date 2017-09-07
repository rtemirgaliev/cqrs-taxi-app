package com.epam.javacc.microservices.common.driver.event;

import com.epam.javacc.microservices.common.driver.model.DriverStatus;

import java.io.Serializable;

public class DriverCreatedEvent implements Serializable {

    private static final long serialVersionUID = 7534361014002829485L;

    private String driverId;
    private String fullName;
    private DriverStatus driverStatus;
    private String assignedOrderId;

    public DriverCreatedEvent() {
    }

    public DriverCreatedEvent(String driverId, String fullName, DriverStatus driverStatus, String assignedOrderId) {
        this.driverId = driverId;
        this.fullName = fullName;
        this.driverStatus = driverStatus;
        this.assignedOrderId = assignedOrderId;
    }

    public String getDriverId() {
        return driverId;
    }
    public String getFullName() {
        return fullName;
    }
    public DriverStatus getDriverStatus() {
        return driverStatus;
    }
    public String getAssignedOrderId() {
        return assignedOrderId;
    }
}
