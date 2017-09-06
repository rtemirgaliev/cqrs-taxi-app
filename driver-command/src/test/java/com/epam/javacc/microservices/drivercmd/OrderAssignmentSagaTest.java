package com.epam.javacc.microservices.drivercmd;

import com.epam.javacc.microservices.drivercmd.assignment.OrderAssignmentSaga;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;

public class OrderAssignmentSagaTest {

    private SagaTestFixture<OrderAssignmentSaga> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new SagaTestFixture<>(OrderAssignmentSaga.class);
    }

//    @Test
//    public void testAssignOrderRequest() throws Exception {
//        fixture.givenNoPriorActivity()
//                .whenPublishingA(new OrderAssignmentStartedEvent("a1", "o1", "d1"))
//                .expectActiveSagas(1)
//                .expectDispatchedCommands(new ChangeOrderStatusCommand("o1", OrderStatus.ASSIGNED_TO_DRIVER, "a1"));
//    }

//    @Test
//    public void testSetDriverOrderAfterChangingOrderStatus() throws Exception {
//        fixture.givenAPublished(new OrderAssignmentStartedEvent("a1", "o1", "d1"))
//                .whenPublishingA(new OrderStatusChangedEvent("o1", OrderStatus.ASSIGNED_TO_DRIVER, "a1"))
//                .expectDispatchedCommands(new ChangeDriverOrderCommand("d1", "o1", "a1"));
//    }

//    @Test
//    public void testAssignmentCompletedAfterOrderAssigned() throws Exception {
//        fixture.givenAPublished(new OrderAssignmentStartedEvent("a1", "o1", "d1"))
//                .andThenAPublished(new OrderStatusChangedEvent("o1", OrderStatus.ASSIGNED_TO_DRIVER, "a1"))
//                .whenPublishingA(new DriverOrderChangedEvent("d1", "o1", "a1"))
//                .expectDispatchedCommands(new CompleteOrderAssignmentCommand("a1"));
//    }

//    @Test
//    public void testSagaEndsAfterTransactionCompleted() throws Exception {
//        fixture.givenAPublished(new OrderAssignmentStartedEvent("a1", "o1", "d1"))
//                .andThenAPublished(new OrderStatusChangedEvent("o1", OrderStatus.ASSIGNED_TO_DRIVER, "a1"))
//                .andThenAPublished(new DriverOrderChangedEvent("d1", "o1", "a1"))
//                .whenPublishingA(new OrderAssignmentCompletedEvent("a1"))
//                .expectActiveSagas(0)
//                .expectNoDispatchedCommands();
//    }
}