package com.epam.esm.exceptions;

import jakarta.servlet.ServletException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TestException.class)
    protected ResponseEntity<Object> handleConflict(TestException ex, WebRequest request) {
        String bodyOfResponse = "Error code: " + ex.getErrorCode() + " ";
        bodyOfResponse =  bodyOfResponse + "Error message: " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<Object> handleConflictNoSuchField(RuntimeException ex, WebRequest request) {
       String bodyOfResponse =  "Error message: " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ServletException.class)
    protected ResponseEntity<Object> handleConflictRuntime(RuntimeException ex, WebRequest request) {
        String bodyOfResponse =  "Error message: " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
