package com.y.Y.error;

import com.y.Y.error.custom_exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundExcpetion(EntityNotFoundException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND,e));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementException(NoSuchElementException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND,e));
    }

    @ExceptionHandler(StringLengthExceededException.class)
    public ResponseEntity<ApiError> handleStringLengthExceededException(StringLengthExceededException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),e));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class, MissingServletRequestPartException.class})
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(Exception e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST,e));
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ApiError> handleDuplicateDataException(DuplicateDataException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),"Duplicate data found of type: " + e.getDataType(),e));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(HttpStatus.UNAUTHORIZED,e));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),e));
    }

    @ExceptionHandler(AuthenticationTokenException.class)
    public ResponseEntity<ApiError> handleAuthenticationTokenException(AuthenticationTokenException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),e));
    }

    @ExceptionHandler(MissingCookieException.class)
    public ResponseEntity<ApiError> handleMissingCookieException(MissingCookieException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),e));
    }

    @ExceptionHandler(SessionAuthenticationException.class)
    public ResponseEntity<ApiError> handleSessionAuthenticationException(SessionAuthenticationException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(HttpStatus.UNAUTHORIZED,e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        logException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST,e));
    }


    private void logException(Exception e) {
        logger.error(e.getMessage());
        logger.error(e.getLocalizedMessage());
    }

}
