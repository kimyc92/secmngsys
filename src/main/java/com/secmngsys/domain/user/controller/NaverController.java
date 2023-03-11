package com.secmngsys.domain.user.controller;

import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.EndpointHelper;
import com.secmngsys.global.model.NaverHelper;
import com.secmngsys.global.model.ResponseSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/naver")
public class NaverController {

    private final NaverHelper naverHelper;
    private final EndpointHelper endpointHelper;

    @Autowired
    public NaverController(NaverHelper naverHelper, EndpointHelper endpointHelper) {
        this.naverHelper = naverHelper;
        this.endpointHelper = endpointHelper;
    }

    @PostMapping(value="/naver")
    public ResponseSuccess naver(){
        //System.out.println("camelEndpointHelper");
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    @PostMapping(value="/camel")
    public ResponseSuccess camel(@RequestBody UserDto userDto){
        userDto.setUserId("TEST");
        return GlobalResponseHandler.of(userDto, SuccessCode.SUCCESS);
    }

}
