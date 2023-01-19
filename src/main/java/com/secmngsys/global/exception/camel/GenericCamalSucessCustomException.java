package com.secmngsys.global.exception.camel;

import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.model.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class GenericCamalSucessCustomException {

    /**
     * CamelException 예외 상황시 발생, 400 status code와 함께 반환
     */
    protected void handleCamelException(Throwable cause, Exchange exchange){
        log.error("handleCamelException", String.valueOf(cause));
        final ResponseError response = ResponseError.of(ErrorCode.BAD_REQUEST, String.valueOf(cause));
        exchange.getMessage().setBody(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).getBody());
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 그 외 모든 Exception 예외 상황시 발생, 500 status code와 함께 반환
     */
    protected void handleException(Throwable cause, Exchange exchange){
        log.error("handleException(", String.valueOf(cause));
        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR, String.valueOf(cause));
        exchange.getMessage().setBody(new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR).getBody());
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
