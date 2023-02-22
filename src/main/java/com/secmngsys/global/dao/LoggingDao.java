package com.secmngsys.global.dao;

import com.secmngsys.global.configuration.database.RoutingDatabaseConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.secmngsys.global.mapper.LoggingMapper;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
@Repository
public class LoggingDao {

    private static final ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();

    public void insertLoggingInfo(String ip, String camelLog){
        //, @Qualifier("routeSqlSessionFactory") SqlSessionFactory sqlSessionFactory

        //RoutingDatabaseConfig rc = new RoutingDatabaseConfig();
        //SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory);




        System.out.println("들어옴??????????");
        System.out.println(threadLocal.get());

        //LoggingMapper mapper = sqlSession.getMapper(LoggingMapper.class);
        //mapper.insertLoggingInfo(ip, camelLog);
    }
//    public void insertLoggingInfo(String ip, String camelLog){
//        LoggingMapper mapper = sqlSession.getMapper(LoggingMapper.class);
//        mapper.insertLoggingInfo(ip, camelLog);
//    }

}
