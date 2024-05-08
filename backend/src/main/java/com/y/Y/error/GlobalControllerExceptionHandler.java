package com.y.Y.error;

import com.y.Y.error.custom_exceptions.BadRequestException;
import com.y.Y.error.custom_exceptions.DuplicateDataException;
import com.y.Y.error.custom_exceptions.StringLengthExceededException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundExcpetion(EntityNotFoundException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND,e));
    }

    @ExceptionHandler(StringLengthExceededException.class)
    public ResponseEntity<ApiError> handleStringLengthExceededException(StringLengthExceededException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),e));
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ApiError> handleDuplicateDataException(DuplicateDataException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),e));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(DuplicateDataException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),e));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(DuplicateDataException e) {
        logException(e);
        return ResponseEntity.status(e.getStatus()).body(new ApiError(e.getStatus(),e));
    }

    private void logException(RuntimeException e) {
        logger.error(e.getMessage());
        logger.error(e.getLocalizedMessage());
    }

}
