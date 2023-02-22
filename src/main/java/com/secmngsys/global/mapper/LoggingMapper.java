package com.secmngsys.global.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.secmngsys.global.sql.LoggingSql;

@Mapper
public interface LoggingMapper {

    @InsertProvider(type = LoggingSql.class, method = "insertLoggingInfo")
    void insertLoggingInfo(@Param("ip") String ip, @Param("camelLog") String camelLog);

}
