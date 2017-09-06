package com.epam.javacc.microservices.drivercmd.web.DTO;

public class AssignOrderRequest {

    private String orderId;


    public AssignOrderRequest() {
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "AssignOrderRequest{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
