package com.epam.javacc.microservices.drivercmd.web.DTO;

import com.epam.javacc.microservices.common.order.model.OrderStatus;

/**
 *
 * @author Rinat Temirgaliev
 */
public class UpdateDriverRequest {

    private String fullName;
    private String driverStatus;


    public UpdateDriverRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }


    @Override
    public String toString() {
        return "UpdateDriverRequest{" +
                "fullName='" + fullName + '\'' +
                ", driverStatus='" + driverStatus + '\'' +
                '}';
    }
}
