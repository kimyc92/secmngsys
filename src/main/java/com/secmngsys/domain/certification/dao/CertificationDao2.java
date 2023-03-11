package com.secmngsys.domain.certification.dao;

import com.secmngsys.domain.certification.mapper.CertificationMapper;
import com.secmngsys.domain.certification.model.dto.CertificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CertificationDao2 {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    @Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    //@Transactional(rollbackFor={Exception.class})
    public void insertSmsDbSendsInfo(CertificationDto certificationDto) {
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 상태 insertSmsDbSendsInfo "+ TransactionSynchronizationManager.isActualTransactionActive());
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 명 insertSmsDbSendsInfo "+ TransactionSynchronizationManager.getCurrentTransactionName());
        CertificationMapper mapper = sqlSession.getMapper(CertificationMapper.class);
        mapper.insertSmsDbSendsInfo(certificationDto);
        //throw new CustomErrorException("테스트", ErrorCode.EXCEPTION);
    }
}
