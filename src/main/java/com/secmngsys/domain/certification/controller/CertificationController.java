package com.secmngsys.domain.certification.controller;

import com.secmngsys.domain.certification.model.dto.CertificationDto;
import com.secmngsys.domain.certification.service.CertificationService;
import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.service.UserService;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping("/certification")
public class CertificationController {

    UserService userService;
    CertificationService certificationService;
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void CertificationController(UserService userService
            , CertificationService certificationService
            , RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.certificationService = certificationService;
        this.redisTemplate = redisTemplate;
    }

    //@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    @PutMapping(value="/sms-sends")
    public ResponseSuccess smsSends(@RequestBody @Valid UserDto userDto) throws SQLException {
        //certificationService.smsSends(userDto);
//        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
//        vop.set("yellow", "banana");
//        vop.set("red", "apple");
//        vop.set("green", "watermelon");
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    @PostMapping(value="/sms-confirms")
    public ResponseSuccess smsConfirms(@RequestBody @Valid CertificationDto certificationDto){
        certificationService.smsConfirms(certificationDto);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    @PostMapping(value="/sms-tests")
    public ResponseSuccess smsTests(@RequestBody @Valid CertificationDto certificationDto) throws SQLException {
        certificationService.smsTests(certificationDto);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    @GetMapping("/auth-no/{key}")
    public ResponseSuccess getRexdisKey(@PathVariable String key) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        String value = (String) vop.get(key);
        return GlobalResponseHandler.of(value, SuccessCode.SUCCESS);
    }

    @PostMapping(value="/auth-check")
    public String authCheck(@RequestBody UserDto userDto){

        log.info("[EXECUTE] /auth-check");
        System.out.println("userId   :" +userDto.getUserId());
        System.out.println("userNm  :" +userDto.getUserNm());
        System.out.println("userHpNo :" +userDto.getUserHpNo());
        return "requestBody";
    }

    @PostMapping(value="/pw-check")
    public String pwCheck(@RequestBody UserDto userDto){

        log.info("[EXECUTE] /pw-check");
        System.out.println("userId   :" +userDto.getUserId());
        System.out.println("userNm  :" +userDto.getUserNm());
        System.out.println("userHpNo :" +userDto.getUserHpNo());
        return "requestBody";
    }

    @PatchMapping(value="/pw-change")
    public String pwChange(@RequestBody UserDto userDto){

        log.info("[EXECUTE] /pw-change");
        System.out.println("userId   :" +userDto.getUserId());
        System.out.println("userNm  :" +userDto.getUserNm());
        System.out.println("userHpNo :" +userDto.getUserHpNo());
        return "requestBody";
    }


}
