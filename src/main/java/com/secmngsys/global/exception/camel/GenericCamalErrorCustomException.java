package com.secmngsys.global.exception.camel;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.model.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;


@Slf4j
@Component
public class GenericCamalErrorCustomException {

    /**
     * CamelException 예외 상황시 발생, 400 status code와 함께 반환
     */
    protected ResponseError handleCamelException(Throwable cause){
        log.error("handleCamelException : {}", cause);
        final ResponseError response = ResponseError.of(ErrorCode.BAD_REQUEST, String.valueOf(cause));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).getBody();
    }

    /**
     * Json To Object 변환시 일치되는 값이 없을 때 발생, 400 status code와 함께 반환
     */
    protected ResponseError handleUnrecognizedPropertyException(Throwable cause) {
        log.error("handleUnrecognizedPropertyException : {}", cause);
        final ResponseError response = ResponseError.of(ErrorCode.BAD_REQUEST, String.valueOf(cause));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).getBody();
    }

    /**
     * Java Validation 검증시 발생하는 에러, 400 status code와 함께 반환
     */
    protected ResponseError handleConstraintViolationException(Throwable cause) {
        log.error("handleConstraintViolationException : {}", cause);
        final ResponseError response = ResponseError.of(ErrorCode.BAD_REQUEST, String.valueOf(cause));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).getBody();
    }

    public ResponseError handleHttpOperationFailedException(Throwable cause) {
        log.error("handleHttpOperationFailedException : {}", cause);
        final ResponseError response = ResponseError.of(ErrorCode.BAD_REQUEST, String.valueOf(cause));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).getBody();
    }


    public ResponseError handleHttpErrorException(int statusCode) {
        log.error("handleHttpException : {}", statusCode);
        System.out.println(HttpStatus.valueOf(statusCode));
        System.out.println(statusCode);
        final ResponseError response = ResponseError.of(ErrorCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.valueOf(201)).getBody();
    }

    /**
     * 그 외 모든 Exception 예외 상황시 발생, 500 status code와 함께 반환
     */
    protected ResponseError handleException(Throwable cause){
        log.error("handleException : {}", cause);
        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR, String.valueOf(cause));
        //exchange.getMessage().setBody(new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR).getBody());
        //exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR).getBody();
    }

}
