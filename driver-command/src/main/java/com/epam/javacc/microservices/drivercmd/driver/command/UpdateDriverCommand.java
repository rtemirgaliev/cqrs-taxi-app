package com.epam.javacc.microservices.drivercmd.driver.command;

import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class UpdateDriverCommand {

    @TargetAggregateIdentifier
    private String driverId;
    @NotNull(message = "Full Name is mandatory")
    @NotBlank(message = "Full Name can not be blank")
    private String fullName;
    private DriverStatus driverStatus;


    public UpdateDriverCommand(String driverId, String name, DriverStatus driverStatus) {
        this.driverId = driverId;
        this.fullName = name;
        this.driverStatus = driverStatus;
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
}
