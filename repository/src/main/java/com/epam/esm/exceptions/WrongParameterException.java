package com.epam.esm.exceptions;


public class WrongParameterException extends BaseCustomException{
    private static final String baseMessage = "Wrong parameter while %s";

    public WrongParameterException(String action, String errorCode) {
        super(String.format(baseMessage, action), errorCode);
    }
}
