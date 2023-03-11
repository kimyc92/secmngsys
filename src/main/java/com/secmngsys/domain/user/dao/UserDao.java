package com.secmngsys.domain.user.dao;

import com.secmngsys.domain.user.mapper.UserMapper;
import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.model.vo.UserDrmVo;
import com.secmngsys.domain.user.model.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Repository
public class UserDao {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;


    public List<UserVo> selectUserInfo(UserDto userDto) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<UserVo> userVo = mapper.selectUserInfo(userDto);
        return userVo;
    }

    public List<UserDrmVo> selectDrmDbOneUserInfo(UserDto userDto) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<UserDrmVo> userDrmVo = mapper.selectDrmDbOneUserInfo(userDto);

//        if(userVo.isEmpty()) {
//            throw new RuntimeException("결과 값이 없습니다.");
//            //throw new GenericSuccessCustomException("결과 값이 없습니다.", SuccessCode.NO_CONTENT);
//        }
        return userDrmVo;
    }

    public List<Object> selectOneUserInfo(UserDto userDto) {
        Object userVo;

        // DRM
        if(userDto.getSysCd().equals("01")) {
            //userVo = userDao.selectDrmDbOneUserInfo(userDto);
            userVo = this.selectDrmDbOneUserInfo(userDto);
        } else {
            userVo = this.selectUserInfo(userDto);
        }

        return (List<Object>) userVo;
    }

    public void updateDrmDbUserInfo(UserDto userDto) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateDrmDbUserInfo(userDto);
    }


}
