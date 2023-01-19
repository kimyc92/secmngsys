package com.secmngsys.global.handler;

import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.model.ResponseSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class GlobalResponseHandler {

    // Void
    public static ResponseSuccess of(final SuccessCode success) {
        final ResponseSuccess response = ResponseSuccess.of(success);
        return new ResponseEntity<>(response, success.getStatus()).getBody();
    }

    // Object Data
    public static ResponseSuccess of(final Object obj, final SuccessCode success) {
        final ResponseSuccess response = ResponseSuccess.of(success, obj);
        return new ResponseEntity<>(response, success.getStatus()).getBody();
    }

}
