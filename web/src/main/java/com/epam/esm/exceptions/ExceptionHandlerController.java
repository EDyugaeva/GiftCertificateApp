package com.epam.esm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for handling custom exceptions in the application.
 * Extends {@link ResponseEntityExceptionHandler} to provide consistent responses.
 */
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    /**
     * Handles exceptions of type {@link DataNotFoundException}.
     *
     * @param ex The instance of {@link DataNotFoundException}.
     * @return The response with the appropriate HTTP status code and error details.
     */
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected BaseCustomException handleDataNotFoundException(BaseCustomException ex) {
        return ex;
    }


    /**
     * Handles exceptions of type {@link WrongParameterException}.
     *
     * @param ex The instance of {@link WrongParameterException}.
     * @return The response with the appropriate HTTP status code and error details.
     */
    @ExceptionHandler(WrongParameterException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected BaseCustomException handleWrongParameterException(BaseCustomException ex) {
        return ex;
    }

    /**
     * Handles exceptions of type {@link OtherDatabaseException}.
     *
     * @param ex The instance of {@link OtherDatabaseException}.
     * @return The response with the appropriate HTTP status code and error details.
     */
    @ExceptionHandler(OtherDatabaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BaseCustomException handleOtherDatabaseException(BaseCustomException ex) {
        return ex;
    }

}
