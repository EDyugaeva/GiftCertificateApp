package com.epam.esm.exceptions;


public class WrongParameterException extends BaseCustomException{
    public WrongParameterException(String message, String errorCode) {
        super(message, errorCode);
    }
}
