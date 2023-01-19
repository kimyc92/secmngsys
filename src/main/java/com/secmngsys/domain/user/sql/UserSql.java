package com.secmngsys.domain.user.sql;

import com.secmngsys.domain.user.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

@Slf4j
public class UserSql {

//    public String selectUserInfo(UserDto userDto) {
//        return new SQL() {{
//            log.info("[SELECT] com.secmngsys.common.sql.selectUserInfo");
//            SELECT("A.USER_ID, A.USER_NM, A.USER_HP_NO");
//            FROM("USER A");
////            WHERE("A.JOB_EXE_DATE = #{jobExeDate}");
////            WHERE("A.JOB_EXE_SEQ  = #{jobExeSeq}");
//        }}.toString();
//    }

    public String selectUserInfo(@Param("userDto") UserDto userDto) {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.domain.sql.selectUserInfo");
            SELECT("A.USER_ID, A.USER_NM, A.USER_HP_NO");
              FROM("USER A");
             WHERE("A.USER_ID = #{userDto.userId}");
             WHERE("A.USER_NM  = #{userDto.userNm}");
             WHERE("A.USER_HP_NO  = #{userDto.userHpNo}");
        }}.toString();
    }
}
