package com.epam.esm.exceptions;

public class WrongModelParameterException extends BaseCustomException{
    public WrongModelParameterException(String message, String errorCode) {
        super(message, errorCode);
    }
}
