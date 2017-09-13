package com.epam.javacc.microservices.ordercmd.order;

import com.epam.javacc.microservices.common.order.event.OrderCreatedEvent;
import com.epam.javacc.microservices.common.order.event.OrderUpdatedEvent;
import com.epam.javacc.microservices.common.order.model.OrderStatus;
import com.epam.javacc.microservices.ordercmd.order.command.UpdateOrderCommand;
import com.epam.javacc.microservices.ordercmd.order.OrderAggregate;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;


public class OrderAggregateTest {

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void setup() {
        fixture = new AggregateTestFixture<OrderAggregate>(OrderAggregate.class);
    }

    @Test
    public void UpdateOrderCommandGeneratesOrderUpdatedEvent() {
        fixture.given(new OrderCreatedEvent("1", "111-111","addr 1", OrderStatus.NEW))
                .when(new UpdateOrderCommand("1", "222-222", "addr 2", OrderStatus.ASSIGNED_TO_DRIVER)
                        )
                .expectSuccessfulHandlerExecution()
                .expectEvents( new OrderUpdatedEvent("1","222-222","addr 2", OrderStatus.ASSIGNED_TO_DRIVER)
                );
    }


}
