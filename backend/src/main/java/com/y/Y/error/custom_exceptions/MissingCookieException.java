package com.y.Y.error.custom_exceptions;

import org.springframework.http.HttpStatus;

public class MissingCookieException extends CustomException{
    final String cookieName;

    public MissingCookieException(HttpStatus httpStatus, String cookieName) {
        super("cookie: '" + cookieName + "' does not exist",httpStatus);
        this.cookieName = cookieName;
    }

    public MissingCookieException(Throwable cause, HttpStatus httpStatus, String cookieName) {
        super("cookie: '" + cookieName + "' does not exist", cause, httpStatus);
        this.cookieName = cookieName;
    }

    public MissingCookieException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus, String cookieName) {
        super("cookie: '" + cookieName + "' does not exist", cause, enableSuppression, writableStackTrace, httpStatus);
        this.cookieName = cookieName;
    }
}
