package com.epam.javacc.microservices.common.driver.event;

import com.epam.javacc.microservices.common.driver.model.DriverStatus;

import java.io.Serializable;

public class DriverStatusChangedEvent implements Serializable {

    private static final long serialVersionUID = 3016795800676200305L;

    private String driverId;
    private DriverStatus driverStatus;
    private String transactionId;

    public DriverStatusChangedEvent() {
    }

    public DriverStatusChangedEvent(String driverId, DriverStatus driverStatus, String transactionId) {
        this.driverId = driverId;
        this.driverStatus = driverStatus;
        this.transactionId = transactionId;
    }


    public String getDriverId() {
        return driverId;
    }
    public DriverStatus getDriverStatus() {
        return driverStatus;
    }
    public String getTransactionId() {
        return transactionId;
    }
}
