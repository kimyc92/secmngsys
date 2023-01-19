package com.secmngsys.domain.certification.dao;

import com.secmngsys.domain.certification.mapper.CertificationMapper;
import com.secmngsys.domain.certification.model.dto.CertificationDto;
import com.secmngsys.domain.user.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CertificationDao2 {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    @Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    //@Transactional(rollbackFor={Exception.class})
    public void insertSmsDbSendsInfo(CertificationDto certificationDto) {
        //System.out.println("insertSmsDbSendsInfo DAO 트랜잭션 상태 "+TransactionSynchronizationManager.isActualTransactionActive());
        //System.out.println("insertSmsDbSendsInfo DAO 트랜잭션 수준 "+TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
        System.out.println("insertSmsDbSendsInfo Service 트랜잭션 상태 insertSmsDbSendsInfo "+ TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("insertSmsDbSendsInfo Service 트랜잭션 명 insertSmsDbSendsInfo "+ TransactionSynchronizationManager.getCurrentTransactionName());

        CertificationMapper mapper = sqlSession.getMapper(CertificationMapper.class);
        System.out.println("test !! - ");
        mapper.insertSmsDbSendsInfo(certificationDto);
        //throw new CustomErrorException("테스트", ErrorCode.EXCEPTION);
    }
}
