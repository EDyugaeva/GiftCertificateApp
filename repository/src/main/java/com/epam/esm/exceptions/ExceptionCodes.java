package com.epam.esm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCodes {
    NOT_FOUND_GIFT_CERTIFICATE("404000"), NOT_FOUND_TAG("404001"), NOT_FOUND_PAIR("404002"), OTHER_EXCEPTION("500000");
    private String errorCode;
}
