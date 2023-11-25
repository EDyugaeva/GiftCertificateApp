package com.epam.esm.exceptions;

import lombok.Getter;

import java.io.IOException;
@Getter
public class TestException extends IOException {
    private final String errorCode;
    public TestException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
