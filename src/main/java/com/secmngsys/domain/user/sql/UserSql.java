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
            log.info("[SELECT] com.secmngsys.domain.user.sql.selectUserInfo");
            SELECT("A.USER_ID, A.USER_NM, A.USER_HP_NO");
              FROM("USER A");
             WHERE("A.USER_ID = #{userDto.userId}");
             WHERE("A.USER_NM  = #{userDto.userNm}");
             WHERE("A.USER_HP_NO  = #{userDto.userHpNo}");
        }}.toString();
    }

    public String selectDrmDbOneUserInfo(@Param("userDto") UserDto userDto) {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.domain.user.sql.selectUserInfo");
            SELECT("#{userDto.sysCd} AS SYS_CD, A.USER_ID");
            SELECT("A.USER_NM, A.USER_HP_NO, A.COMPANY_NM, A.DEPT_NM");
            FROM("USER A");
            WHERE("A.USER_ID = #{userDto.userId}");
            WHERE("A.USER_NM  = NVL(#{userDto.userNm}, A.USER_NM)");
            WHERE("A.USER_HP_NO  = NVL(#{userDto.userHpNo}, A.USER_HP_NO)");
        }}.toString();
    }

    public String updateDrmDbUserInfo(@Param("userDto") UserDto userDto) {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.domain.user.sql.updateDrmDbUserInfo");
            UPDATE("USER A");
               SET("A.PASSWORD = #{userDto.password}");
             WHERE("A.USER_ID = #{userDto.userId}");
             WHERE("A.USER_NM  = #{userDto.userNm}");
             WHERE("A.USER_HP_NO  = #{userDto.userHpNo}");
        }}.toString();
    }

}
