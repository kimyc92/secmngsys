package com.secmngsys.global.route.test;

import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.service.UserService;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HelloBean {

//    UserService userService;
//
//    @Autowired
//    public HelloBean(UserService userService) {
//        this.userService = userService;
//    }
//
//    public ResponseSuccess sayHello(@RequestBody @Valid UserDto userDto) {
//        System.out.println("666666");
//        userService.selectOneUserInfo(userDto);
//        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
//    }


}
