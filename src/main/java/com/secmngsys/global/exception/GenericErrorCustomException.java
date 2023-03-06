package com.secmngsys.global.exception;

import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.model.ResponseError;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
public class GenericErrorCustomException extends RuntimeException {

    private ErrorCode errorCode;
    // NULL 대신 빈 배열이나 컬렉션을 반환, NULL이 표현하는 것이 애매함
    private List<ResponseError.FieldError> fieldError = Collections.emptyList();

    public GenericErrorCustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        log.debug("GenericErrorCustomException() - 1");
    }

    public GenericErrorCustomException(String message, ErrorCode errorCode
            , List<ResponseError.FieldError> fieldError) {
        super(message);
        this.errorCode = errorCode;
        this.fieldError = fieldError;
        log.debug("GenericErrorCustomException() - 2");
    }

}
