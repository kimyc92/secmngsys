package com.secmngsys.domain.user.route;

public class ResponseType {

    private String message;

    public ResponseType() {
    }

    public ResponseType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}