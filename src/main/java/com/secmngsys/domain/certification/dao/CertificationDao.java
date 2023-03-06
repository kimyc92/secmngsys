package com.secmngsys.domain.certification.dao;

import com.secmngsys.domain.certification.mapper.CertificationMapper;
import com.secmngsys.domain.certification.model.dto.CertificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CertificationDao {

    @Resource(name="routeSqlSessionTemplate")
    SqlSessionTemplate sqlSession;

    private final String PREFIX = "sms:";
    private final int LIMIT_TIME = 3 * 60;
    private final RedisTemplate<String, Object> redisTemplate;

    public void createSmsCertification(String userHpNo, String certificationNumber) {
        System.out.println("확인 : "+PREFIX + userHpNo);
        redisTemplate.opsForValue()
                .set(PREFIX + userHpNo, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getSmsCertification(String userHpNo) {
        return (String) redisTemplate.opsForValue().get(PREFIX + userHpNo);
    }

    public void removeSmsCertification(String userHpNo) {
        redisTemplate.delete(PREFIX + userHpNo);
    }

    public boolean hasKey(String userHpNo) {
        return redisTemplate.hasKey(PREFIX + userHpNo);
    }

    //@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    @Transactional(rollbackFor={Exception.class})
    public void insertSmsSendsInfo(CertificationDto certificationDto) {
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 상태 insertSmsSendsInfo "+ TransactionSynchronizationManager.isActualTransactionActive());
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 명 insertSmsSendsInfo "+ TransactionSynchronizationManager.getCurrentTransactionName());
        CertificationMapper mapper = sqlSession.getMapper(CertificationMapper.class);
        mapper.insertSmsSendsInfo(certificationDto);
        //throw new CustomErrorException("테스트", ErrorCode.EXCEPTION);
    }

    @Transactional(rollbackFor={Exception.class})
    public void insertSmsDbSendsInfo(CertificationDto certificationDto) {
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 상태 insertSmsDbSendsInfo "+ TransactionSynchronizationManager.isActualTransactionActive());
        //System.out.println("insertSmsDbSendsInfo Service 트랜잭션 명 insertSmsDbSendsInfo "+ TransactionSynchronizationManager.getCurrentTransactionName());
        CertificationMapper mapper = sqlSession.getMapper(CertificationMapper.class);
        mapper.insertSmsDbSendsInfo(certificationDto);
        //throw new CustomErrorException("테스트", ErrorCode.EXCEPTION);
    }

    public CertificationDto selectSmsSendsSeq() {
        CertificationMapper mapper = sqlSession.getMapper(CertificationMapper.class);
        return mapper.selectSmsSendsSeq();
    }

    public void deleteSmsSendsInfo(CertificationDto certificationDto) {
        CertificationMapper mapper = sqlSession.getMapper(CertificationMapper.class);
        mapper.deleteSmsSendsInfo(certificationDto);
    }

    public void updateSmsSendsInfo(CertificationDto certificationDto) {
        CertificationMapper mapper = sqlSession.getMapper(CertificationMapper.class);
        mapper.updateSmsSendsInfo(certificationDto);
    }

    public void updateSmsSendsInfoCompensation() {
        CertificationMapper mapper = sqlSession.getMapper(CertificationMapper.class);
        mapper.updateSmsSendsInfoCompensation();
    }
}
