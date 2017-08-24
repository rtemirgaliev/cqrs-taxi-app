package com.epam.javacc.microservices.common.driver.event;


import java.io.Serializable;

public class DriverOrderChangedEvent implements Serializable {

    private static final long serialVersionUID = -4702717846901123323L;

    private String driverId;
    private String assignedOrderId;
    private String transactionId;

    public DriverOrderChangedEvent() {
    }

    public DriverOrderChangedEvent(String driverId, String assignedOrderId, String transactionId) {
        this.driverId = driverId;
        this.assignedOrderId = assignedOrderId;
        this.transactionId = transactionId;
    }

    public String getDriverId() {
        return driverId;
    }
    public String getAssignedOrderId() {
        return assignedOrderId;
    }
    public String getTransactionId() {
        return transactionId;
    }
}
