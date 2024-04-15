package com.example.backend.exception;

import java.util.Map;

public class UnauthorizedException extends RuntimeException{
    private String errorCode;
    private Map<String, Object> additionalDetails;

    public UnauthorizedException(String message) {
        super(message);
        this.errorCode = "UNAUTHORIZED";
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "UNAUTHORIZED";
    }

    public UnauthorizedException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public UnauthorizedException(String message, String errorCode, Map<String, Object> additionalDetails) {
        super(message);
        this.errorCode = errorCode;
        this.additionalDetails = additionalDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getAdditionalDetails() {
        return additionalDetails;
    }
}
