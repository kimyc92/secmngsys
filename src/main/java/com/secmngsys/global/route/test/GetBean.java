package com.secmngsys.global.route.test;

import org.springframework.stereotype.Component;

@Component
public class GetBean {

    public ResponseType sayHello() {
        // Your logic can go here! If you return a POJO, Camel will try and serialise it to JSON.
        System.out.println("옴옴옴옴");
        return new ResponseType("Hello, world!");
    }

}