package com.epam.esm.exceptions;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;

import java.io.IOException;

@Getter
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public abstract class BaseCustomException extends IOException {
    private final String errorCode;

    public BaseCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
