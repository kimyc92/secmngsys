package com.secmngsys.domain.certification.service;

import com.secmngsys.domain.certification.dao.CertificationDao;
import com.secmngsys.domain.certification.dao.CertificationDao2;
import com.secmngsys.domain.certification.model.dto.CertificationDto;
import com.secmngsys.domain.user.dao.UserDao;
import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.exception.InvalidValueException;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseError;
import com.secmngsys.global.model.ResponseSuccess;
import com.secmngsys.global.util.GenerateCertNumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Validated
@Transactional
@Service("certificationService")
public class CertificationService {

    UserDao userDao;
    CertificationDao certificationDao;
    CertificationDao2 certificationDao2;

    @Autowired
    private PlatformTransactionManager txManager;


    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    @Autowired
    public void CertificationController(UserDao userDao
            ,CertificationDao certificationDao
            ,CertificationDao2 certificationDao2) {
        this.userDao = userDao;
        this.certificationDao = certificationDao;
        this.certificationDao2 = certificationDao2;
    }

    //@Transactional(rollbackFor={Exception.class}
    public void smsSends(Exchange exchange, @Valid @Body UserDto userDto) {
        System.out.println("exchange "+exchange);
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 상태 smsSends "+ TransactionSynchronizationManager.isActualTransactionActive());
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 명 smsSends "+ TransactionSynchronizationManager.getCurrentTransactionName());
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 수준 "+ TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
       // try {
        userDao.selectOneUserInfo(userDto);
        String certificationNumber = new GenerateCertNumberUtil(6).excuteGenerate();
        CertificationDto certificationDto = certificationDao.selectSmsSendsSeq();
        certificationDto.setCertificationNumber(certificationNumber);
        BeanUtils.copyProperties(userDto, certificationDto);
        certificationDao.createSmsCertification(certificationDto.getUserHpNo(), certificationDto.getCertificationNumber());
        certificationDao.insertSmsSendsInfo(certificationDto);
        exchange.getIn().setBody(certificationDto);
          //  certificationDao2.insertSmsDbSendsInfo(userDto);
//        if(1==1)
//            throw new InvalidValueException("일부로 에러를 냈습니당.");
       // return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }
    public ResponseSuccess cancelSmsSends(@Valid CertificationDto certificationDto) {
        certificationDao.deleteSmsSendsInfo(certificationDto);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    public ResponseSuccess smsDbSends(@Valid CertificationDto certificationDto) {
        certificationDao.insertSmsDbSendsInfo(certificationDto);
//        if(1==1)
//            throw new InvalidValueException("인증번호 생성오류");
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    public void smsTests(@Valid CertificationDto certificationDto) {
        certificationDao.insertSmsDbSendsInfo(certificationDto);
    }

    public void smsConfirms(CertificationDto certificationDto) {
        if (isVerify(certificationDto)) {
            throw new InvalidValueException("인증번호가 일치하지 않습니다.");
        }
        certificationDao.removeSmsCertification(certificationDto.getCertificationNumber());
    }

    private boolean isVerify(CertificationDto certificationDto) {
        return !(certificationDao.hasKey(certificationDto.getUserHpNo()) &&
                certificationDao.getSmsCertification(certificationDto.getUserHpNo())
                        .equals(certificationDto.getCertificationNumber()));
    }
}
