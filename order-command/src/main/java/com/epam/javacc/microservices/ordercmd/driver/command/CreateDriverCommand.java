package com.epam.javacc.microservices.ordercmd.driver.command;

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

    public CreateDriverCommand(String name) {
        this.driverId = UUID.randomUUID().toString();
        this.fullName = name;
    }

    //TODO This constructor with  ID only need us for test purposes. Consider removing
    public CreateDriverCommand(String driverId, String name) {
        this.driverId = driverId;
        this.fullName = name;
    }

    public String getDriverId() {
        return driverId;
    }
    public String getFullName() {
        return fullName;
    }
}
