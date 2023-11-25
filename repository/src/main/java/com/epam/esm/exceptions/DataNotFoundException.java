package com.epam.esm.exceptions;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {
    private final String errorCode;
    public DataNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
