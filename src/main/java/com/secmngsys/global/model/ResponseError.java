package com.secmngsys.global.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secmngsys.global.configuration.code.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// 객체를 json 형태의 ResponseBody로 매핑하는데 MappingJackson2HttpMessageConverter가 사용되는데 객체의 정보를 가져오기 위해 Getter를 사용한다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseError { //implements Serializable {

    private String message;
    private HttpStatus status;
    private List<FieldError> data;
    private String code;

    @JsonIgnore
    protected void setErrorCode(ErrorCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
        this.status = code.getStatus();
    }
//
//    @JsonIgnore
//    protected void setErrorCode(HttpStatus status, String code, String message){
//        this.code = code;
//        this.message = message;
//        this.status = status;
//    }

    private ResponseError(final ErrorCode code) {
        setErrorCode(code);
        this.data = new ArrayList<>();
    }

    public ResponseError(final HttpStatus status, String code, String message) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private ResponseError(ErrorCode code, String exceptionMessage) {
        setErrorCode(code);
        this.data = List.of(new FieldError("", "", exceptionMessage));
        //this.errors = List.of(FieldError.builder().title("zz").value("hh").content("dqe123").build());
    }

    private ResponseError(final ErrorCode code, final List<FieldError> data) {
        setErrorCode(code);
        this.data = data;
    }

    public static ResponseError of(final ErrorCode code, final BindingResult bindingResult) {
        return new ResponseError(code, FieldError.of(bindingResult));
    }

    public static ResponseError of(final ErrorCode code) {
        //return new ErrorResponse(code);
        return new ResponseError(code, Collections.emptyList());
    }

    public static ResponseError of(final ErrorCode code, final List<FieldError> data) {
      //  System.out.println("7777 - " +List.of(errors.stream().map(FieldError::new).collect(Collectors.toList())).get(0).get(0).getValue());
        //System.out.println("8888 - " +List.of(errors).get(0).get(0).getValue());
        return new ResponseError(code, data);
    }

    public static ResponseError of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ResponseError.FieldError> errors = ResponseError.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ResponseError(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    public static ResponseError of(ErrorCode code, String message) {
        return new ResponseError(code, message);
    }

    public static ResponseError of(final HttpStatus status, final String code, final String message) {
        return new ResponseError(status, code, message);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String title;
        private String value;
        private String content;

        @Builder
        public FieldError(final String title, final String value, final String content) {
            this.title = title;
            this.value = value;
            this.content = content;
        }

        public FieldError(FieldError fieldError) {
            this.title = fieldError.title;
            this.value = fieldError.value;
            this.content = fieldError.content;
        }

        public static List<FieldError> of(final String title, final String value, final String content) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(title, value, content));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(data -> new FieldError(
                            data.getField(),
                            data.getRejectedValue() == null ? "" : data.getRejectedValue().toString(),
                            data.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
