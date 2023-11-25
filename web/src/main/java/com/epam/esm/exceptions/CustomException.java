package com.epam.esm.exceptions;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
