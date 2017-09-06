package com.epam.javacc.microservices.drivercmd.web.DTO;

import com.epam.javacc.microservices.common.order.model.OrderStatus;

/**
 *
 * @author Rinat Temirgaliev
 */
public class CreateDriverRequest {

    private String fullName;

    public CreateDriverRequest() {
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "CreateDriverRequest{" +
                "fullName='" + fullName + '\'' +
                '}';
    }
}
