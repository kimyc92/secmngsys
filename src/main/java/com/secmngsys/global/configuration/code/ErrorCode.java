package com.secmngsys.global.configuration.code;

import com.secmngsys.global.model.ResponseError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public enum ErrorCode {

    /***************
     * 1. Common   *
     ***************/
    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COM_001_400", "Illegal or Inappropriate Argument"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COM_002_400", "Invalid Input Value"),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST,    "COM_003_400", "Entity Not Found"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST,  "COM_004_400", "Invalid Type Value"),
    DUPLICATED_FOLDER_NAME(HttpStatus.BAD_REQUEST, "COM_005_400", "중복폴더명이 이미 존재합니다."),
    BELOW_MIN_MY_PRICE(HttpStatus.BAD_REQUEST, "COM_006_400", "최저 희망가는 최소 원 이상으로 설정해 주세요."),
    ILLEGAL_STATE(HttpStatus.BAD_REQUEST, "COM_007_400", "Illegal State"),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "COM_008_400", "Illegal Argument"),

    // 403 Forbidden
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN,  "COM_001_403", "Access is Denied"),

    // 404 Not Found
    NOT_FOUND(HttpStatus.NOT_FOUND, "COM_001_404", "The Requested Resource is Not Available"),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "COM_002_404", "해당 관심상품 아이디가 존재하지 않습니다."),
    NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "COM_003_404", "해당 폴더 아이디가 존재하지 않습니다."),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COM_001_405", " Method Not Allowed"),

    // 500 Internal Server Error
    EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "COM_001_500", "Exception"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COM_002_500", "Server Error"),

    // HTTP ERROR
    HTTP_CLIENT_ERROR(HttpStatus.BAD_REQUEST, "COM_001_HTTP", "Client Error Responses")

    ;

    @Setter
    private HttpStatus status;
    private String code;
    private String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
