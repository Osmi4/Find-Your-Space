package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiRequestException extends RuntimeException {
    private final String errorCode;
    public ApiRequestException(String message) {
        super(message);
        this.errorCode = "";
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "";
    }

    public ApiRequestException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiRequestException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
