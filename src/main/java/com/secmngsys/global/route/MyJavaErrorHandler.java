package com.secmngsys.global.route;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyJavaErrorHandler extends RouteConfigurationBuilder {
    @Override
    public void configuration() throws Exception {
        routeConfiguration("javaError")
                .onException(Exception.class).handled(true)
                .log("Java WARN: ${exception.message}");
    }
}
