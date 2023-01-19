package com.secmngsys.global.exception;

import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.model.ResponseError;

import java.util.List;

public class InvalidValueException extends GenericErrorCustomException {

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE
                , ResponseError.FieldError.of("", "", message));
    }

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InvalidValueException(String message, ErrorCode errorCode,
                                 List<ResponseError.FieldError> fieldError) {
        super(message, errorCode, fieldError);
    }

}
