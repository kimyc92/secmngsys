package com.secmngsys.domain.certification.sql;

import com.secmngsys.domain.certification.model.dto.CertificationDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

@Slf4j
public class CertificationSql {


    public String selectSmsSendsSeq() {
        log.info("[SELECT] com.secmngsys.domain.certification.sql.selectSmsSendsSeq");
        return new SQL() {{
            SELECT("DATE_FORMAT(NOW(),'%Y%m%d') AS SMS_SEND_DATE");
            SELECT("DATE_FORMAT(NOW(),'%H%i%S') AS SMS_SEND_TIME");
            SELECT("LPAD(NEXTVAL(SQ_SMS_SEND_INFO_001),3,'0') AS SMS_SEND_SEQ");
              FROM("DUAL");
        }}.toString();
    }
    public String insertSmsSendsInfo(@Param("certificationDto") CertificationDto certificationDto) {
        return new SQL() {{
            log.info("[INSERT] com.secmngsys.domain.certification.sql.insertSmsSendsInfo");
            INSERT_INTO("TB_SMS_SEND_INFO");
            INTO_COLUMNS("SMS_SEND_DATE, SMS_SEND_TIME, SMS_SEND_SEQ");
            INTO_COLUMNS("USER_ID, USER_NM, USER_HP_NO, SMS_SEND_STATUS_CD, SMS_SEND_STATUS_NM");
            INTO_COLUMNS("REG_DATE, REG_USER_ID, CHG_DATE, CHG_USER_ID");
            INTO_VALUES("#{certificationDto.smsSendDate}, #{certificationDto.smsSendTime}");
            INTO_VALUES("#{certificationDto.smsSendSeq}, #{certificationDto.userId}");
            INTO_VALUES("#{certificationDto.userNm}, #{certificationDto.userHpNo}");
            INTO_VALUES("'N', 'ing..'");
            INTO_VALUES("NOW(), #{certificationDto.userId}");
            INTO_VALUES("NOW(), #{certificationDto.userId}");
        }}.toString();
    }

    public String insertSmsDbSendsInfo(@Param("certificationDto") CertificationDto certificationDto) {
        return new SQL() {{
            log.info("[INSERT] com.secmngsys.domain.certification.sql.insertSmsDbSendsInfo");
            INSERT_INTO("TB_TEST");
            INTO_COLUMNS("SMS_SEND_DATE,SMS_SEND_TIME,SMS_SEND_SEQ,USER_ID,USER_NM");
            INTO_COLUMNS("USER_HP_NO,REG_DATE,REG_USER_ID,CHG_DATE,CHG_USER_ID");
            INTO_VALUES("#{certificationDto.smsSendDate}, #{certificationDto.smsSendTime}");
            INTO_VALUES("#{certificationDto.smsSendSeq}, #{certificationDto.userId}");
            INTO_VALUES("#{certificationDto.userNm}, #{certificationDto.userHpNo}");
            INTO_VALUES("SYSDATE, #{certificationDto.userId}, SYSDATE, #{certificationDto.userId}");
        }}.toString();
    }

    public String deleteSmsSendsinfo(@Param("certificationDto") CertificationDto certificationDto) {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.domain.certification.sql.deleteSmsDbSendsInfo");
            DELETE_FROM("TB_SMS_SEND_INFO");
            WHERE("SMS_SEND_DATE=#{certificationDto.smsSendDate}");
            WHERE("SMS_SEND_TIME=#{certificationDto.smsSendTime}");
            WHERE("SMS_SEND_SEQ=#{certificationDto.smsSendSeq}");
        }}.toString();
    }

    public String updateSmsSendsInfo(@Param("certificationDto") CertificationDto certificationDto) {
        return new SQL() {{
            log.info("[UPDATE] com.secmngsys.domain.certification.sql.updateSmsSendsinfo");
            UPDATE("TB_SMS_SEND_INFO A");
               SET("A.SMS_SEND_STATUS_CD = #{certificationDto.smsSendStatusCd}");
               SET("A.SMS_SEND_STATUS_NM = #{certificationDto.smsSendStatusNm}");
            WHERE("SMS_SEND_DATE=#{certificationDto.smsSendDate}");
            WHERE("SMS_SEND_TIME=#{certificationDto.smsSendTime}");
            WHERE("SMS_SEND_SEQ=#{certificationDto.smsSendSeq}");
        }}.toString();
    }

    public String updateSmsSendsInfoCompensation() {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.domain.certification.sql.updateSmsSendsInfoCompensation");
            UPDATE("TB_SMS_SEND_INFO A");
               SET("A.SMS_SEND_STATUS_CD = 'E'");
               SET("A.SMS_SEND_STATUS_NM = 'ERROR'");
             WHERE("(A.SMS_SEND_DATE, A.SMS_SEND_TIME, A.SMS_SEND_SEQ) IN "
                + "(SELECT X.SMS_SEND_DATE, X.SMS_SEND_TIME, X.SMS_SEND_SEQ "
                + "FROM TB_SMS_SEND_INFO X "
                + "WHERE (X.SMS_SEND_DATE BETWEEN DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 7 DAY),'%Y%m%d')"
                + "AND DATE_FORMAT(NOW(),'%Y%m%d'))"
                + "AND X.SMS_SEND_STATUS_CD = 'N')"
             );
        }}.toString();
    }




}
