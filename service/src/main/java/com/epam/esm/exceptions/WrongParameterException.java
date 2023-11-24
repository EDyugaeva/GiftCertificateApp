package com.epam.esm.exceptions;

import static com.epam.esm.exceptions.ExceptionCodes.WRONG_PARAMETER;

public class WrongParameterException extends RuntimeException{
    public WrongParameterException() {
        super(WRONG_PARAMETER.getErrorCode());
    }
}
