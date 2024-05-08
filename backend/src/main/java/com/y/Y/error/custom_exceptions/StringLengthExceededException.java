package com.y.Y.error.custom_exceptions;

import org.springframework.http.HttpStatus;

public class StringLengthExceededException extends CustomException{

    final String password;

    public StringLengthExceededException(HttpStatus httpStatus, String password) {
        super(httpStatus);
        this.password = password;
    }

    public StringLengthExceededException(String message, HttpStatus httpStatus, String password) {
        super(message, httpStatus);
        this.password = password;
    }

    public StringLengthExceededException(String message, Throwable cause, HttpStatus httpStatus, String password) {
        super(message, cause, httpStatus);
        this.password = password;
    }

    public StringLengthExceededException(Throwable cause, HttpStatus httpStatus, String password) {
        super(cause, httpStatus);
        this.password = password;
    }

    public StringLengthExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus, String password) {
        super(message, cause, enableSuppression, writableStackTrace, httpStatus);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
