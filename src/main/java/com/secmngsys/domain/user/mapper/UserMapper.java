package com.secmngsys.domain.user.mapper;

import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.model.vo.UserVo;
import com.secmngsys.domain.user.sql.UserSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface UserMapper {

    @SelectProvider(type = UserSql.class, method = "selectUserInfo")
    List<UserVo> selectUserInfo(@Param("userDto") UserDto userDto);

    @SelectProvider(type = UserSql.class, method = "selectDrmDbOneUserInfo")
    List<UserVo> selectDrmDbOneUserInfo(@Param("userDto") UserDto userDto);

    @UpdateProvider(type = UserSql.class, method = "updateDrmDbUserInfo")
    void updateDrmDbUserInfo(@Param("userDto") UserDto userDto);

}