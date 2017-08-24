package com.epam.javacc.microservices.ordercmd.assignment;

import com.epam.javacc.microservices.common.driver.event.DriverOrderChangedEvent;
import com.epam.javacc.microservices.common.order.event.OrderStatusChangedEvent;
import com.epam.javacc.microservices.common.order.model.OrderStatus;
import com.epam.javacc.microservices.ordercmd.assignment.command.CompleteAssignOrderCommand;
import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderCompletedEvent;
import com.epam.javacc.microservices.ordercmd.assignment.event.AssignOrderRequestedEvent;
import com.epam.javacc.microservices.ordercmd.driver.command.ChangeDriverOrderCommand;
import com.epam.javacc.microservices.ordercmd.order.command.ChangeOrderStatusCommand;
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
                .whenPublishingA(new AssignOrderRequestedEvent("a1", "o1", "d1"))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new ChangeOrderStatusCommand("o1", OrderStatus.ASSIGNED_TO_DRIVER, "a1"));
    }

    @Test
    public void testSetDriverOrderAfterChangingOrderStatus() throws Exception {
        fixture.givenAPublished(new AssignOrderRequestedEvent("a1", "o1", "d1"))
                .whenPublishingA(new OrderStatusChangedEvent("o1", OrderStatus.ASSIGNED_TO_DRIVER, "a1"))
                .expectDispatchedCommands(new ChangeDriverOrderCommand("d1", "o1", "a1"));
    }

    @Test
    public void testAssignmentCompletedAfterOrderAssigned() throws Exception {
        fixture.givenAPublished(new AssignOrderRequestedEvent("a1", "o1", "d1"))
                .andThenAPublished(new OrderStatusChangedEvent("o1", OrderStatus.ASSIGNED_TO_DRIVER, "a1"))
                .whenPublishingA(new DriverOrderChangedEvent("d1", "o1", "a1"))
                .expectDispatchedCommands(new CompleteAssignOrderCommand("a1"));
    }

    @Test
    public void testSagaEndsAfterTransactionCompleted() throws Exception {
        fixture.givenAPublished(new AssignOrderRequestedEvent("a1", "o1", "d1"))
                .andThenAPublished(new OrderStatusChangedEvent("o1", OrderStatus.ASSIGNED_TO_DRIVER, "a1"))
                .andThenAPublished(new DriverOrderChangedEvent("d1", "o1", "a1"))
                .whenPublishingA(new AssignOrderCompletedEvent("a1"))
                .expectActiveSagas(0)
                .expectNoDispatchedCommands();
    }
}