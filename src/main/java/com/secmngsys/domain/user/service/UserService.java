package com.secmngsys.domain.user.service;

import com.secmngsys.domain.user.dao.UserDao;
import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.model.vo.UserVo;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.exception.InvalidValueException;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseSuccess;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Validated
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<UserVo> selectUserInfo(@Valid UserDto userDto) {
        return userDao.selectUserInfo(userDto);
    }

    public ResponseSuccess selectOneUserInfo(@Valid @Body UserDto userDto) {
        Object userVo = userDao.selectOneUserInfo(userDto);
//
//        // DRM
//        if(userDto.getSysCd().equals("01")) {
//            userVo = userDao.selectDrmDbOneUserInfo(userDto);
//        } else {
//            userVo = userDao.selectOneUserInfo(userDto);
//        }

        return GlobalResponseHandler.of(userVo, SuccessCode.SUCCESS);
//        if(userVo.size() < 1) throw new CustomSuccessException("요청 결과가 없습니다."
//                , SuccessCode.NO_CONTENT);
    }

    public ResponseSuccess selectOneUserInfo2(@Valid @Body UserDto userDto) {
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }
    public ResponseSuccess selectOneUserInfo2(String body) {
       // List<UserVo> userVo = userDao.selectOneUserInfo(userDto);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
//        if(userVo.size() < 1) throw new CustomSuccessException("요청 결과가 없습니다."
//                , SuccessCode.NO_CONTENT);
    }

    public ResponseSuccess userInfoChange(@Valid @Body UserDto userDto){
        List<UserVo> userVo;
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(date);

        System.out.println(dateFormatted);
        //if(1==1)
        //    throw new InvalidValueException("시스템 코드가 없거나 비정상적입니다.");

        // DRM
        if(userDto.getSysCd().equals("01")) {

            String sha256hex_str = DigestUtils.sha256Hex(userDto.getPassword());
            userDto.setPassword(sha256hex_str);
            userDao.updateDrmDbUserInfo(userDto);

        } else {
            throw new InvalidValueException("시스템 코드가 없거나 비정상적입니다.");
        }
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

}
