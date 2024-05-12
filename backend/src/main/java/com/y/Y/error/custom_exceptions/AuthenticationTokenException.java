package com.y.Y.error.custom_exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationTokenException extends CustomException{
    public AuthenticationTokenException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public AuthenticationTokenException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public AuthenticationTokenException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public AuthenticationTokenException(Throwable cause, HttpStatus httpStatus) {
        super(cause, httpStatus);
    }

    public AuthenticationTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace, httpStatus);
    }
}
