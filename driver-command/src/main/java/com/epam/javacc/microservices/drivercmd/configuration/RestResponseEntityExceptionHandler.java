package com.epam.javacc.microservices.drivercmd.configuration;

import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import java.util.stream.Collectors;

/**
 *
 * @author Rinat Temirgaliev
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    public RestResponseEntityExceptionHandler() {
        super();
    }


    @ExceptionHandler({ JSR303ViolationException.class })
    public ResponseEntity<Object> handleValidation(final JSR303ViolationException ex, final WebRequest request) {
        // TODO do it properly !!!!
//        final String bodyOfResponse = ex.getViolations().toString();
        final String bodyOfResponse = ex.getViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        LOG.error("Validation error", ex);
        return new ResponseEntity<Object>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        final String bodyOfResponse = "HttpMessageNotReadableException";
        LOG.error(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        final String bodyOfResponse = "MethodArgumentNotValidException";
        LOG.error(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    // TODO Consider handling validation and other errors/exceptions here



}