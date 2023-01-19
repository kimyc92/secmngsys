package com.secmngsys.global.exception;

import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.model.ResponseError;
import com.secmngsys.global.model.ResponseSuccess;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelException;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.RuntimeCamelException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
//@Order(Ordered.LOWEST_PRECEDENCE) // Ordered.HIGHEST_PRECEDENCE, Ordered.LOWEST_PRECEDENCE
@RestControllerAdvice(basePackages = "com.secmngsys")
public class GlobalException {

    /**
     * 사용자의 요청 파라미터가 적합지 않거나(illegal) 적절하지 못한(inappropriate) 상태일 때 발생
     */
    @ExceptionHandler(value = {IllegalStateException.class})
    protected ResponseEntity<ResponseError> handleIllegalStatementException(IllegalStateException e){
        log.error("illegalStateException", e);
        final ResponseError response = ResponseError.of(ErrorCode.ILLEGAL_STATE, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 객체의 상태가가 메소드를 수행하기에 적합지 않거나(illegal) 적절하지 못한(inappropriate) 상태일 때 발생
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ResponseError> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        final ResponseError errorObject = ResponseError.of(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        System.out.println("왜여기 안들어옴 ㅡㅡ");
        final ResponseError response = ResponseError.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ResponseError> handleBindException(BindException e) {
        log.error("handleBindException", e);
        final ResponseError response = ResponseError.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ResponseError> hanleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        final ResponseError response = ResponseError.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ResponseError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ResponseError response = ResponseError.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ResponseError> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        final ResponseError response = ResponseError.of(ErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(String.valueOf(ErrorCode.HANDLE_ACCESS_DENIED)));
    }

    /**
     * Business에 맞게 필요한 예외처리 호출시 발생
     */
    @ExceptionHandler(value = {GenericErrorCustomException.class})
    protected ResponseEntity<ResponseError> handleGenericErrorCustomException(final GenericErrorCustomException e) {
        log.info("GenericErrorCustomException", e);
        final ErrorCode errorCode = e.getErrorCode();
        final List<ResponseError.FieldError> fieldError = e.getFieldError();
        final ResponseError response = ResponseError.of(errorCode, fieldError);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    /**
     * Business에 맞게 필요한 예외처리 호출시 발생
     */
    @ExceptionHandler(GenericSuccessCustomException.class)
    protected ResponseEntity<ResponseSuccess> handleGenericSuccessCustomException(final GenericSuccessCustomException e) {
        log.debug("CustomSuccessException", e);
        final SuccessCode successCode = e.getSuccessCode();
        final ResponseSuccess response = ResponseSuccess.of(successCode);
//        System.out.println("getCode - "+response.getCode());
//        System.out.println("getData - "+response.getData());
//        System.out.println("getMessage - "+response.getMessage());
        return new ResponseEntity<>(response, successCode.getStatus());
    }

    @ExceptionHandler(org.apache.camel.http.base.HttpOperationFailedException.class)
    protected ResponseEntity<ResponseError> handleHttpOperationFailedException(final org.apache.camel.http.base.HttpOperationFailedException e) {
        log.info("HttpOperationFailedException", e);
        //final SuccessCode successCode = e.getSuccessCode();
        System.out.println("handleHttpOperationFailedException!@!@@!@!@!@!@!@!@");
        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 그 외 모든 Exception 예외 상황시 발생, 500 status code와 함께 반환
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseError> handleException(Exception e) {
        log.error("Exception", e);
        System.out.println("요깅네?");
        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeCamelException.class)
    public ResponseEntity<ResponseError> camelExchangeExceptions(RuntimeCamelException e) {
        log.error("RuntimeCamelException", e);
        System.out.println("요깅네?");
        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({CamelException.class, CamelExecutionException.class})
    public ResponseEntity<ResponseError> camelExchangeExceptions(CamelException ex) {

        System.out.println("zzzzzzzzzzzzz - "+ ex.getClass());
//        if (ex instanceof org.apache.camel.http.common.HttpOperationFailedException) {
//            org.apache.camel.http.common.HttpOperationFailedException exception = (org.apache.camel.http.common.HttpOperationFailedException) ex;
//            return ResponseEntity
//                    .status(exception.getStatusCode())
//                    .body(stringToJsonNode(exception.getResponseBody()));
//        }
//
//        if (ex.getCause() instanceof org.apache.camel.http.common.HttpOperationFailedException) {
//            org.apache.camel.http.common.HttpOperationFailedException exception = (org.apache.camel.http.common.HttpOperationFailedException) ex.getCause();
//            return ResponseEntity
//                    .status(exception.getStatusCode())
//                    .body(stringToJsonNode(exception.getResponseBody()));
//        }

        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

//        return ResponseEntity
//                .status(499)
//                .body(new ErrorCode(ex.getMessage()));
    }

//    @ExceptionHandler(RuntimeCamelException.class)
//    protected ResponseEntity<ResponseError> handleRuntimeCamelException(final RuntimeCamelException e) {
//        //log.error("GenericCamelCustomException", e);
//        //final SuccessCode successCode = e.getSuccessCode();
//        System.out.println("!!!!!!!!!!!RuntimeCamelException!@!@@!@!@!@!@!@!@");
//        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    /**
     * 사용자의 요청 Body 정보가 JSON TYPE이 아닐 경우에 발생
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        final ResponseError response = ResponseError.of(ErrorCode.INTERNAL_SERVER_ERROR
                , "Required request body is missing, JSON Type Check");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
