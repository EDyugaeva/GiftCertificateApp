package com.epam.esm.exceptions;

import static com.epam.esm.exceptions.ExceptionCodes.NOT_SUPPORTED;

public class NotSupportedSortingException extends RuntimeException {
    public NotSupportedSortingException() {
        super(NOT_SUPPORTED.getErrorCode());
    }
}
