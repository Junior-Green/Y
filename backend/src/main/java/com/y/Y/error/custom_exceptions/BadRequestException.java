package com.y.Y.error.custom_exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException{
    public BadRequestException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public BadRequestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public BadRequestException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public BadRequestException(Throwable cause, HttpStatus httpStatus) {
        super(cause, httpStatus);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace, httpStatus);
    }
}
