package com.epam.esm.exceptions;

public class ApplicationException extends BaseCustomException{
    public ApplicationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
