package com.epam.javacc.microservices.drivercmd.web;

import com.epam.javacc.microservices.common.driver.model.DriverStatus;
import com.epam.javacc.microservices.drivercmd.assignment.command.StartOrderAssignmentCommand;
import com.epam.javacc.microservices.drivercmd.driver.command.ChangeDriverStatusCommand;
import com.epam.javacc.microservices.drivercmd.driver.command.CreateDriverCommand;
import com.epam.javacc.microservices.drivercmd.driver.command.UpdateDriverCommand;
import com.epam.javacc.microservices.drivercmd.finishing.command.StartOrderFinishingCommand;
import com.epam.javacc.microservices.drivercmd.web.DTO.AssignOrderRequest;
import com.epam.javacc.microservices.drivercmd.web.DTO.ChangeDriverStatusRequest;
import com.epam.javacc.microservices.drivercmd.web.DTO.CreateDriverRequest;
import com.epam.javacc.microservices.drivercmd.web.DTO.UpdateDriverRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping(value = "/driver")
public class DriverController {

    private static final Logger LOG = LoggerFactory.getLogger(DriverController.class);

    @Autowired
    private CommandGateway commandGateway;

    /**
     *  The 'commandGateway.send' command sends command to a command bus. This command is then handled by a handler
     *  annotated as @CommandHandler in OrderAggregate
    */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<String> create(@RequestBody CreateDriverRequest request) {
        LOG.debug("CreateDriverRequest request received: " + request.toString());
        CreateDriverCommand command = new CreateDriverCommand(request.getFullName());
        return commandGateway.send(command);
    }

    @PutMapping(value = "/{driverId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CompletableFuture<String> update(@PathVariable String driverId, @RequestBody UpdateDriverRequest request) {
        LOG.debug("UpdateDriverRequest received: " + request.toString());
        UpdateDriverCommand command =
                new UpdateDriverCommand(driverId, request.getFullName(), DriverStatus.valueOf(request.getDriverStatus()));
        return commandGateway.send(command);
    }

    @PutMapping(value = "/{driverId}/status")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CompletableFuture<String> changeStatus(@PathVariable String driverId, @RequestBody ChangeDriverStatusRequest request) {
        LOG.debug( "ChangeDriverStatusRequest received: " + request.toString());
        ChangeDriverStatusCommand command =
                new ChangeDriverStatusCommand(driverId,  DriverStatus.valueOf(request.getDriverStatus()), null);
        return commandGateway.send(command);

    }

    @PostMapping(value = "/{driverId}/order")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CompletableFuture<String> assignOrder(@PathVariable String driverId, @RequestBody AssignOrderRequest request) {
        LOG.debug("AssignOrderRequest received: " + request.toString());
        StartOrderAssignmentCommand command =
                new StartOrderAssignmentCommand(request.getOrderId(), driverId);
        return commandGateway.send(command);
    }

    @PostMapping(value = "/{driverId}/finish-order")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CompletableFuture<String> finishOrder(@PathVariable String driverId) {
        LOG.debug("Finish Order Request received for driver: " + driverId);
        StartOrderFinishingCommand command = new StartOrderFinishingCommand(driverId);
        return commandGateway.send(command);
    }


}
