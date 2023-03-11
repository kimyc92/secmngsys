package com.secmngsys.domain.user.sql;

import com.secmngsys.domain.user.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

@Slf4j
public class UserSql {

    public String selectUserInfo(@Param("userDto") UserDto userDto) {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.domain.user.sql.selectUserInfo");
            SELECT("A.USER_ID               AS USER_ID");
            SELECT("A.USER_NAME             AS USER_NM");
            SELECT("A.PHONE_NUMBER          AS USER_HP_NO");
            FROM("FIDP_USER A");
            FROM("FIDP_DEPT B");
            FROM("FIDP_POSITION C");
            FROM("FIDP_ROLE D");
            WHERE("A.DSDCODE       = '0100000000003160'");
            WHERE("B.DSDCODE       = A.DSDCODE");
            WHERE("C.DSDCODE       = A.DSDCODE");
            WHERE("D.DSDCODE       = A.DSDCODE");
            WHERE("A.USER_ID       = #{userDto.userId}");
            WHERE("A.USER_NAME     = #{userDto.userNm}");
            WHERE("A.PHONE_NUMBER  = #{userDto.userHpNo}");
        }}.toString();
    }

    public String selectDrmDbOneUserInfo(@Param("userDto") UserDto userDto) {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.domain.user.sql.selectUserInfo");
            SELECT("#{userDto.sysCd}        AS SYS_CD");
            SELECT("A.USER_ID               AS USER_ID");
            SELECT("A.USER_NAME             AS USER_NM");
            SELECT("A.PHONE_NUMBER          AS USER_HP_NO");
            SELECT("A.DEPT_CODE             AS DEPT_CODE");
            SELECT("B.DEPT_NAME             AS DEPT_NM");
            FROM("FIDP_USER A");
            FROM("FIDP_DEPT B");
            FROM("FIDP_POSITION C");
            FROM("FIDP_ROLE D");
            WHERE("A.DSDCODE       = '0100000000003160'");
            WHERE("B.DSDCODE       = A.DSDCODE");
            WHERE("C.DSDCODE       = A.DSDCODE");
            WHERE("D.DSDCODE       = A.DSDCODE");
            WHERE("A.USER_ID       = #{userDto.userId}");
            WHERE("A.USER_NAME     = NVL(#{userDto.userNm}, A.USER_NAME)");
            WHERE("A.PHONE_NUMBER  = NVL(#{userDto.userHpNo}, A.PHONE_NUMBER)");
        }}.toString();
    }

    public String updateDrmDbUserInfo(@Param("userDto") UserDto userDto) {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.domain.user.sql.updateDrmDbUserInfo");
            UPDATE("FIDP_USER A");
            SET("A.USER_PWD = #{userDto.password}");
            SET("A.USED_PWD = #{userDto.password}");
            SET("A.PWD_ERR_CNT = 0");
            SET("A.INITIAL_PASSWORD_YN = 'N'");
           // SET("A.PWD_UPDATED_TS = TIMESTAMP(6)");
            WHERE("A.DSDCODE       = '0100000000003160'");
            WHERE("A.USER_ID       = #{userDto.userId}");
            WHERE("A.USER_NAME     = #{userDto.userNm}");
            WHERE("A.PHONE_NUMBER  = #{userDto.userHpNo}");
        }}.toString();
    }

}
