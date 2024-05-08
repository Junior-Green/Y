package com.y.Y.error.custom_exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateDataException extends CustomException {

    public enum DataType {
        USERNAME,
        USER_EMAIL,
        USER_PHONE_NUMBER
    }

    final DataType dataType;

    public DuplicateDataException(HttpStatus httpStatus, DataType dataType) {
        super(httpStatus);
        this.dataType = dataType;
    }

    public DuplicateDataException(String message, HttpStatus httpStatus, DataType dataType) {
        super(message, httpStatus);
        this.dataType = dataType;
    }

    public DuplicateDataException(String message, Throwable cause, HttpStatus httpStatus, DataType dataType) {
        super(message, cause, httpStatus);
        this.dataType = dataType;
    }

    public DuplicateDataException(Throwable cause, HttpStatus httpStatus, DataType dataType) {
        super(cause, httpStatus);
        this.dataType = dataType;
    }

    public DuplicateDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus, DataType dataType) {
        super(message, cause, enableSuppression, writableStackTrace, httpStatus);
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return dataType;
    }
}
