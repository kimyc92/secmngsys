package com.secmngsys.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secmngsys.domain.user.dao.UserDao;
import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.model.vo.UserVo;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseSuccess;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

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
        List<UserVo> userVo = userDao.selectOneUserInfo(userDto);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);


//        if(userVo.size() < 1) throw new CustomSuccessException("요청 결과가 없습니다."
//                , SuccessCode.NO_CONTENT);
    }

    public ResponseSuccess selectOneUserInfo2(@Valid @Body UserDto userDto) {
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }
    public ResponseSuccess selectOneUserInfo2(String body) {
       // List<UserVo> userVo = userDao.selectOneUserInfo(userDto);
        System.out.println(body);
        System.out.println("hihihi");
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);


//        if(userVo.size() < 1) throw new CustomSuccessException("요청 결과가 없습니다."
//                , SuccessCode.NO_CONTENT);
    }
    public void test(String body, Exchange exchange) throws JsonProcessingException {
        System.out.println("시작됨 ㅎㅎ");
        System.out.println(body);
        System.out.println(exchange.getIn());
        System.out.println(exchange.getIn().getBody());
        UserDto userDto = exchange.getIn().getBody(UserDto.class);
//        ObjectMapper mapper = new ObjectMapper();
        //UserDto userDto = (UserDto) exchange.getIn().getBody();
//        UserDto userDto = mapper.readValue(body, UserDto.class);
        System.out.println("chk - "+userDto);
//        Class<?> userDto = body.getClass();
//        System.out.println(userDto.getClass());
//        System.out.println(userDto);
        //userDao.selectOneUserInfo(userDto);
        //return userDto;
    }

    public void test2(@Valid @Body UserDto userDto){
        System.out.println("시작됨22 ㅎㅎ");
        System.out.println(userDto);
        //System.out.println(exchange.getIn());
//        System.out.println(exchange.getIn().getBody());
//        UserDto userDto = (UserDto) exchange.getIn().getBody();
//        System.out.println(userDto);
//
//        Class<?> userDto = body.getClass();
//        System.out.println(userDto.getClass());
//        System.out.println(userDto);
        //userDao.selectOneUserInfo(userDto);
        //return userDto;
    }
}
