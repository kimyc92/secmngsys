package com.secmngsys.global.exception;

import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.model.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelException;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.ValidationException;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@ControllerAdvice
public class CustomHttpRequestException extends ResponseEntityExceptionHandler {

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        //
//        final List<String> errors = new ArrayList<String>();
//        for (final ResponseError.FieldError error : ex.getBindingResult().getFieldErrors()) {
//            errors.add(error.getField() + ": " + error.getDefaultMessage());
//        }
//        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
//        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
//
//        final ResponseError response = ResponseError.of(ErrorCode.METHOD_NOT_ALLOWED, String.valueOf(builder));
//        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
//    }

//    @ExceptionHandler({CamelExecutionException.class, HttpOperationFailedException.class, CamelException.class, RuntimeCamelException.class})
//    public final ResponseEntity<ResponseError> handleCamelExecutionException(CamelExecutionException e, WebRequest request) {
//        log.error("handleCamelExecutionException {}", ExceptionUtils.getStackTrace(e));
//        Throwable exception = e.getExchange().getException();
//
////        if( !(exception instanceof ValidationException) && exception.getCause() != null ){
////            //get the real cause from the CamelExecutionException
////            exception = exception.getCause();
////        }
//        final ResponseError response = ResponseError.of(ErrorCode.METHOD_NOT_ALLOWED, "ddddddddddd");
//        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
//    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        System.out.println("dddd?zzzz");
        final ResponseError response = ResponseError.of(ErrorCode.METHOD_NOT_ALLOWED, String.valueOf("builder"));
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 405 : 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex
            , final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("handleHttpRequestMethodNotSupported()");
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        final ResponseError response = ResponseError.of(ErrorCode.METHOD_NOT_ALLOWED, String.valueOf(builder));
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

//
//    @Override
//    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        //
//        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
//
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        //
//        final String error = ex.getRequestPartName() + " part is missing";
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        //
//        final String error = ex.getParameterName() + " parameter is missing";
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    //
//
//    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
//    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        //
//        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
//
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    @ExceptionHandler({ ConstraintViolationException.class })
//    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        //
//        final List<String> errors = new ArrayList<String>();
//        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
//            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
//        }
//
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    // 404
//
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        //
//        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
//
//        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    // 405
//
//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        //
//        final StringBuilder builder = new StringBuilder();
//        builder.append(ex.getMethod());
//        builder.append(" method is not supported for this request. Supported methods are ");
//        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
//
//        final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    // 415
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        //
//        final StringBuilder builder = new StringBuilder();
//        builder.append(ex.getContentType());
//        builder.append(" media type is not supported. Supported media types are ");
//        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
//
//        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    // 500
//
//    @ExceptionHandler({ Exception.class })
//    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//        logger.error("error", ex);
//        //
//        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
}
