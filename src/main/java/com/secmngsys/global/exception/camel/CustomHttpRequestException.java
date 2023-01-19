package com.secmngsys.global.exception.camel;

import com.secmngsys.global.configuration.code.ErrorCode;

public class CustomHttpRequestException extends RuntimeException {

    public CustomHttpRequestException(String message) {
        super(message);
    }
}
