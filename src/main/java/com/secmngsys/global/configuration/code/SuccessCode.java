package com.secmngsys.global.configuration.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    /***************
     * 1. Common   *
     ***************/
    // 200, Ok
    SUCCESS(HttpStatus.OK, "COM_001_200", "HTTP OK"),

    // 204, No Content
    NO_CONTENT(HttpStatus.OK, "COM_001_204", "No Content"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COM_002_500", "Server Error"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
