package com.epam.javacc.microservices.drivercmd.driver.command;

import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class ChangeDriverStatusCommand {

    @TargetAggregateIdentifier
    private String driverId;
    private DriverStatus driverStatus;
    private String transactionId;

    public ChangeDriverStatusCommand(String driverId, DriverStatus driverStatus, String transactionId) {
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
