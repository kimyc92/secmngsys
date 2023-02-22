package com.secmngsys.global.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

public class HttpClientInfoUtil {

    protected final HttpServletRequest request;

    public HttpClientInfoUtil(HttpServletRequest request) {
        this.request = request;
    }

    public String getClientIP() {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
