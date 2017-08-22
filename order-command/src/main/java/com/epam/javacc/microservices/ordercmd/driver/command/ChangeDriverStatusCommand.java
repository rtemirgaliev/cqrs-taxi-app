package com.epam.javacc.microservices.ordercmd.driver.command;

import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class ChangeDriverStatusCommand {

    @TargetAggregateIdentifier
    private String driverId;
    private DriverStatus driverStatus;

    public ChangeDriverStatusCommand(String driverId, DriverStatus driverStatus) {
        this.driverId = driverId;
        this.driverStatus = driverStatus;
    }

    public String getDriverId() {
        return driverId;
    }
    public DriverStatus getDriverStatus() {
        return driverStatus;
    }
}
