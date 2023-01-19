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

        System.out.println("camelEndpointHelper");
        System.out.println(endpointHelper.toString());
        System.out.println(endpointHelper.getApp().get("MyApp1"));
        System.out.println(endpointHelper.getApp().get("MyApp2"));
       // System.out.println(endpointHelper.getApp().get(0).getEndpoint().get("ep1").getTo_route());
       // System.out.println(camelEndpointHelper.getApp().get(0).getName());
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    @PostMapping(value="/camel")
    public ResponseSuccess camel(@RequestBody UserDto userDto){

        System.out.println("camel test");
        System.out.println("testDto - "+userDto.getUserHpNo());
        System.out.println("testDto - "+userDto.getUserNm());
        System.out.println("testDto - "+userDto.getUserId());
        userDto.setUserId("내맘대로티");
        return GlobalResponseHandler.of(userDto, SuccessCode.SUCCESS);
    }

}
