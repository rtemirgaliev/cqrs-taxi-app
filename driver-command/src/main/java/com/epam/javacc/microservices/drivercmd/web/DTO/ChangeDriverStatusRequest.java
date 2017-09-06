package com.epam.javacc.microservices.drivercmd.web.DTO;

/**
 *
 * @author Rinat Temirgaliev
 */
public class ChangeDriverStatusRequest {

    private String driverStatus;


    public ChangeDriverStatusRequest() {
    }

    public String getDriverStatus() {
        return driverStatus;
    }
    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    @Override
    public String toString() {
        return "ChangeDriverStatusRequest{" +
                "driverStatus='" + driverStatus + '\'' +
                '}';
    }
}
