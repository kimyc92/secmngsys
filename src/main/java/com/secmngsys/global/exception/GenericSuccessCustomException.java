package com.secmngsys.global.exception;

import com.secmngsys.global.configuration.code.SuccessCode;
import lombok.Getter;


@Getter
public class GenericSuccessCustomException extends RuntimeException {

    private SuccessCode successCode;

    public GenericSuccessCustomException(String message, SuccessCode successCode) {
        super(message);
        this.successCode = successCode;
    }

}
