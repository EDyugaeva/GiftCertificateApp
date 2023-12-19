package com.epam.esm.exceptions;

import lombok.Getter;

@Getter
public class DataNotFoundException extends BaseCustomException {
    private static final String baseMessage = "Requested resource (%s) was not found";

    public DataNotFoundException(String resource, String errorCode) {
        super(String.format(baseMessage, resource), errorCode);
    }
}
