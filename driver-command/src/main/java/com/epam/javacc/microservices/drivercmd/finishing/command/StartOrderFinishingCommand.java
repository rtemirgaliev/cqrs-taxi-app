package com.epam.javacc.microservices.drivercmd.finishing.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class StartOrderFinishingCommand {

    @TargetAggregateIdentifier
    private final String finishingId;
    private final String driverId;

    public StartOrderFinishingCommand(String driverId) {
        this.finishingId = UUID.randomUUID().toString();
        this.driverId = driverId;
    }

    public StartOrderFinishingCommand(String finishingId, String driverId) {
        this.finishingId = finishingId;
        this.driverId = driverId;
    }

    public String getFinishingId() {
        return finishingId;
    }

    public String getDriverId() {
        return driverId;
    }
}
