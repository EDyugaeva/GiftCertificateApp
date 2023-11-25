package com.epam.esm.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected BaseCustomException handleDataNotFoundException(BaseCustomException ex) {
        return ex;
    }

    @ExceptionHandler(WrongParameterException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected BaseCustomException handleWrongParameterException(BaseCustomException ex) {
        return ex;
    }

    @ExceptionHandler(OtherDatabaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BaseCustomException handleOtherDatabaseException(BaseCustomException ex) {
        return ex;
    }

    @ExceptionHandler(WrongModelParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BaseCustomException handleWrongModelParameterException(BaseCustomException ex) {
        return ex;
    }
}
