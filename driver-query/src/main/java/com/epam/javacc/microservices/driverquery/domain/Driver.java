package com.epam.javacc.microservices.driverquery.domain;

import com.epam.javacc.microservices.common.driver.event.DriverCreatedEvent;
import com.epam.javacc.microservices.common.driver.model.DriverStatus;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity(name = "Drivers")
public class Driver {

    @Id
    private String driverId;
    private String fullName;
    @Enumerated
    private DriverStatus driverStatus;
    private String assignedOrderId;
    @Version
    private Long optLockVersion;

    public Driver() {
    }

    public Driver(String driverId, String fullName, DriverStatus driverStatus, String assignedOrderId) {
        this.driverId = driverId;
        this.fullName = fullName;
        this.driverStatus = driverStatus;
        this.assignedOrderId = assignedOrderId;
    }

    public Driver(DriverCreatedEvent event) {
        this.driverId = event.getDriverId();
        this.fullName = event.getFullName();
        this.driverStatus = event.getDriverStatus();
        this.assignedOrderId = event.getAssignedOrderId();
    }



    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getAssignedOrderId() {
        return assignedOrderId;
    }

    public void setAssignedOrderId(String assignedOrderId) {
        this.assignedOrderId = assignedOrderId;
    }
}
