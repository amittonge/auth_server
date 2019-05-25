package com.globant.auth.authserver.error;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final String[] errors;

    private final Date timestamp;

    protected ErrorResponse(HttpStatus status, String message, String... errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = new java.util.Date();
    }

    public static ErrorResponse of(HttpStatus status, String message, String... errors) {
        return new ErrorResponse(status, message, errors);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String[] getErrorList() {
        return errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}