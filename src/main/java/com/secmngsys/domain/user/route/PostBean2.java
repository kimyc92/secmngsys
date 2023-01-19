package com.secmngsys.domain.user.route;

import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseSuccess;
import org.springframework.stereotype.Component;

@Component
public class PostBean2 {

    public ResponseType response(PostRequestType input) {
        // We create a new object of the ResponseType
        // Camel will be able to serialise this automatically to JSON
        //return GlobalResponseHandler.of(SuccessCode.SUCCESS);
        return new ResponseType("Thanks for your post, " + input.getName() + "!");
    }
}