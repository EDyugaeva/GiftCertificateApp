package com.epam.esm.exceptions;

public class ApplicationDatabaseException extends BaseCustomException{
    public ApplicationDatabaseException(String message, String errorCode) {
        super(message, errorCode);
    }
}
