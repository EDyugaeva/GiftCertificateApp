package com.epam.esm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCodes {
    NOT_SUPPORTED("500001"), WRONG_PARAMETER("400002");
    private String errorCode;
}
