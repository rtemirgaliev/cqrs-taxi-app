package com.epam.javacc.microservices.ordercmd.web.DTO;

import com.epam.javacc.microservices.common.order.model.OrderStatus;

/**
 *
 * @author Rinat Temirgaliev
 */
public class UpdateOrderRequest {

    private String orderId;
    private String phone;
    private String address;
    private OrderStatus status;

    public UpdateOrderRequest() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
