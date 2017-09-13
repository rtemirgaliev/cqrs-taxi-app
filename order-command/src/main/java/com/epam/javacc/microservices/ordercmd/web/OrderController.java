package com.epam.javacc.microservices.ordercmd.web;

import com.epam.javacc.microservices.ordercmd.order.command.CreateOrderCommand;
import com.epam.javacc.microservices.ordercmd.order.command.DeleteOrderCommand;
import com.epam.javacc.microservices.ordercmd.order.command.UpdateOrderCommand;
import com.epam.javacc.microservices.ordercmd.web.DTO.CreateOrderRequest;
import com.epam.javacc.microservices.ordercmd.web.DTO.UpdateOrderRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private CommandGateway commandGateway;


    /**
     *  The 'commandGateway.send' command sends command to a command bus. This command is then handled by a handler
     *  annotated as @CommandHandler in OrderAggregate
    */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<String> create(@RequestBody CreateOrderRequest request) {
        LOG.debug(CreateOrderRequest.class.getSimpleName() + " request received");

        CreateOrderCommand command = new CreateOrderCommand(request.getPhone(),
                request.getAddress());
        LOG.debug(CreateOrderCommand.class.getSimpleName() + " is sending to command gateway: Order [{}]", command.getOrderId());
        return commandGateway.send(command);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CompletableFuture<String> update(@RequestBody UpdateOrderRequest request) {
        LOG.debug(UpdateOrderRequest.class.getSimpleName() + " request received");

        UpdateOrderCommand command = new UpdateOrderCommand(request.getOrderId(), request.getPhone(),
                request.getAddress(), request.getStatus());
        LOG.debug(UpdateOrderCommand.class.getSimpleName() + " is sending to command gateway: Order [{}]", command.getOrderId());
        return commandGateway.send(command);
    }

    @DeleteMapping(value = "/{orderId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CompletableFuture<String> delete(@PathVariable String orderId) {
        LOG.debug("Delete order request received: " + orderId);
        DeleteOrderCommand command = new DeleteOrderCommand(orderId);
        LOG.debug(DeleteOrderCommand.class.getSimpleName() + " is sending to command gateway: Order [{}]", command.getOrderId());
        return commandGateway.send(command);
    }




}
