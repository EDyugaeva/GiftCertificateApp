package com.epam.esm.exceptions;

public class OtherDatabaseException extends BaseCustomException{
    public OtherDatabaseException(String message, String errorCode) {
        super(message, errorCode);
    }
}
