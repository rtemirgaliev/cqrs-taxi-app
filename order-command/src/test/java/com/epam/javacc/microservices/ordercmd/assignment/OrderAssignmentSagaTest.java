package com.epam.javacc.microservices.ordercmd.assignment;

import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderRequestedEvent;
import com.epam.javacc.microservices.ordercmd.driver.command.AssignOrderCommand;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

public class OrderAssignmentSagaTest {

    private SagaTestFixture<OrderAssignmentSaga> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new SagaTestFixture<>(OrderAssignmentSaga.class);
    }

    @Test
    public void testAssignOrderRequest() throws Exception {
        fixture.givenNoPriorActivity()
                .whenPublishingA(new AssignOrderRequestedEvent("a1","o1", "d1"))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new AssignOrderCommand("o1", orderId));
    }
}