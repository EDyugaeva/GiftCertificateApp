package com.epam.esm.exceptions;

import lombok.Getter;

@Getter
public class DataNotFoundException extends BaseCustomException {
    public DataNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }
}
