package com.epam.javacc.microservices.drivercmd.driver.command;

import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateDriverCommand {

    @TargetAggregateIdentifier
    private String driverId;
    @NotNull(message = "Full Name is mandatory")
    @NotBlank(message = "Full Name can not be blank")
    private String fullName;


    public CreateDriverCommand(String fullName) {
        this.driverId = UUID.randomUUID().toString();
        this.fullName = fullName;
    }

    public CreateDriverCommand(String driverId, String fullName) {
        this.driverId = driverId;
        this.fullName = fullName;
    }

    public String getDriverId() {
        return driverId;
    }
    public String getFullName() {
        return fullName;
    }
}
