package com.epam.javacc.microservices.ordercmd.driver.exception;

public class WrongDriverStatusException extends Exception {

    public WrongDriverStatusException() {
    }

    public WrongDriverStatusException(String message) {
        super(message);
    }
}
