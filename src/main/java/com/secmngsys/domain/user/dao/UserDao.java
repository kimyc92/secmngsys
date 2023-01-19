package com.secmngsys.domain.user.dao;

import com.secmngsys.domain.user.mapper.UserMapper;
import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.model.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Repository
public class UserDao {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    public List<UserVo> selectOneUserInfo(UserDto userDto) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<UserVo> userVo = mapper.selectUserInfo(userDto);
        return userVo;
    }

    public List<UserVo> selectUserInfo(UserDto userDto) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<UserVo> userVo = mapper.selectUserInfo(userDto);
        return userVo;
    }
}
