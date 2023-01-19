package com.secmngsys.global.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secmngsys.global.configuration.code.SuccessCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseSuccess {

    private String message;

    private HttpStatus status;

    private Object data;

    private String code;

    @JsonIgnore
    protected void setSuccessCode(SuccessCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
        this.status = code.getStatus();
    }

    private ResponseSuccess(final SuccessCode code) {
        setSuccessCode(code);
        this.data = new ArrayList<>();
    }

    private ResponseSuccess(SuccessCode code, Object data) {
        setSuccessCode(code);
        this.data = data;
    }

/*
    private ResponseSucess(SuccessCode code, String exceptionMessage) {
        setSuccessCode(code);
        this.data = List.of(new ResponseSucess.FieldSucess("", "", exceptionMessage));
    }

 */

    public static ResponseSuccess of(final SuccessCode code) {
        return new ResponseSuccess(code);
    }

    public static ResponseSuccess of(final SuccessCode code, final Object obj) {
        return new ResponseSuccess(code, obj);
    }

    /*
    private ResponseSucess(final SuccessCode code, final Object obj) {
        setSuccessCode(code);
        this.data = (List<Object>) obj;
    }
     */
    /*
    private ResponseEntity(final SuccessCode code, final List<FieldSucess> data) {
        setSuccessCode(code);
        this.data = data;
    }

    public static ResponseEntity of(final SuccessCode code) {
        return new ResponseEntity(code, Collections.emptyList());
    }

    public static ResponseEntity of(final SuccessCode code, final List<FieldSucess> data) {
        return new ResponseEntity(code, data);
    }

    public static ResponseEntity of(SuccessCode code, String message) {
        return new ResponseEntity(code, message);
    }
 */
}
