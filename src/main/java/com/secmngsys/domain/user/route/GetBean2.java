package com.secmngsys.domain.user.route;

import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseSuccess;
import org.springframework.stereotype.Component;

@Component
public class GetBean2 {

    public ResponseType sayHello() {
        // Your logic can go here! If you return a POJO, Camel will try and serialise it to JSON.
        //return GlobalResponseHandler.of(SuccessCode.SUCCESS);
        return new ResponseType("Hello, world!");
    }

}