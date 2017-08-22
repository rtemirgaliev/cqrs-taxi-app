package com.epam.javacc.microservices.ordercmd.driver.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateDriverCommand {

    @TargetAggregateIdentifier
    private String driverId;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name can not be blank")
    private String name;

    public CreateDriverCommand(String name) {
        this.driverId = UUID.randomUUID().toString();
        this.name = name;
    }

    //This constructor with  ID only need us for test purposes. Consider removing
    public CreateDriverCommand(String driverId, String name) {
        this.driverId = driverId;
        this.name = name;
    }

    public String getDriverId() {
        return driverId;
    }
    public String getName() {
        return name;
    }
}
