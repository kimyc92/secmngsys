package com.secmngsys.global.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

@Slf4j
public class LoggingSql {

    public String insertLoggingInfo(@Param("ip") String ip, @Param("camelLog") String camelLog) {
        return new SQL() {{
            log.info("[SELECT] com.secmngsys.global.sql.insertLoggingInfo");
            INSERT_INTO("TB_LOGGING");
            INTO_COLUMNS("LOGGING_DATE, LOGGING_TIME, LOGGING_SEQ");
            INTO_COLUMNS("CLIENT_IP, LOG");
            INTO_VALUES("DATE_FORMAT(NOW(),'%Y%m%d'), DATE_FORMAT(NOW(),'%H%i%S')");
            INTO_VALUES("LPAD(NEXTVAL(SQ_LOGGING_INFO_001),3,'0'), #{ip}, #{camelLog}");
        }}.toString();
    }

}
