package com.secmngsys.global.aop;

import com.secmngsys.global.configuration.code.DatabaseTypeCode;
import com.secmngsys.global.configuration.context.RoutingDatabaseContextHolder;
import com.secmngsys.global.exception.RoutingDataSourceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(value=1)
// InitializingBean Bean 초기화시 Aop 구현, DisposableBean Bean 소멸시 Aop 구현
public class RoutingDatabaseAspect { //implements InitializingBean, DisposableBean {

    //@Autowired
    //RoutingDatabaseInfo routingDatabaseInfo;

    /*
    @Override
    public void afterPropertiesSet() throws Exception { // InitializingBean
        System.out.println(" !! InitializingBean !! ");
    }

    @Override
    public void destroy() throws Exception { // DisposableBean
        System.out.println(" !! DisposableBean !! ");
    }
    */

    @Pointcut("execution(* com.secmngsys..*..selectSmsDb*(..))" +
           "|| execution(* com.secmngsys..*..updateSmsDb*(..))" +
           "|| execution(* com.secmngsys..*..insertSmsDb*(..))" +
           "|| execution(* com.secmngsys..*..deleteSmsDb*(..))")
    private void smsDbPointCut() {}

    @Pointcut("execution(* com.secmngsys..*..selectDrmDb*(..))" +
            "|| execution(* com.secmngsys..*..updateDrmDb*(..))" +
            "|| execution(* com.secmngsys..*..insertDrmDb*(..))" +
            "|| execution(* com.secmngsys..*..deleteDrmDb*(..))")
    private void drmDbPointCut() {}

    @Pointcut("execution(* com.secmngsys..*..selectSource*(..))" +
           "|| execution(* com.secmngsys..*..updateSource*(..))" +
           "|| execution(* com.secmngsys..*..insertSource*(..))" +
           "|| execution(* com.secmngsys..*..deleteSource*(..))")
    private void sourcePointCut() {}

    @Around("smsDbPointCut()")
    public Object smsDbPointCutAround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Routing to SMS Database");
        // Before
        try {
//            RoutingDatabaseConfig routingDatabaseConfig = new RoutingDatabaseConfig();
//            RoutingDatabaseInfo rdi = (RoutingDatabaseInfo) routingDatabaseConfig.dataSources.get(DatabaseTypeCode.Target);
//            routingDatabaseInfo.RoutingDatabaseInfo(rdi.getJdbcUrl(), rdi.getDriverClassName(), rdi.getUsername(), rdi.getPassword());
            RoutingDatabaseContextHolder.set(DatabaseTypeCode.Sms);
            Object result = pjp.proceed();
            return result;
        } finally {
            // After
            RoutingDatabaseContextHolder.clear();
        }
    }

    @Around("drmDbPointCut()")
    public Object drmDbPointCutAround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Routing to DRM Database");
        // Before
        try {
            RoutingDatabaseContextHolder.set(DatabaseTypeCode.Drm);
            Object result = pjp.proceed();
            return result;
        } finally {
            // After
            RoutingDatabaseContextHolder.clear();
        }
    }

    @After("smsDbPointCut()")
    public void smsDbPointCutAfter(JoinPoint joinPoint) {
        log.debug("Target PointCut After Debug");

    }

    @AfterReturning(value = "smsDbPointCut()", returning = "result")
    public void smsDbPointCutAfterReturning(JoinPoint joinPoint, Object result){
        log.debug("Target PointCut AfterReturning Debug");
        log.debug("Result : "+result); // RESULT 값은 DB 결과값 그대로 받아 옴
    }

    @AfterThrowing(value = "smsDbPointCut()", throwing = "e")
    public void smsDbPointCutAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.debug("Target PointCut AfterThrowing Debug");
        log.debug("Throwable : "+e); //
    }

    @Around("sourcePointCut()")
    public Object sourcePointCutAround(ProceedingJoinPoint pjp) {
        log.debug("Routing to Source Database");
        // Before
        try {
            RoutingDatabaseContextHolder.set(DatabaseTypeCode.Drm);
            Object result = pjp.proceed();
            return result;
        } catch (Throwable e) {
            throw new RoutingDataSourceException("소스 데이터 베이스 전환 중 오류 발생 \n"+e);
        } finally {
            // After
            RoutingDatabaseContextHolder.clear();
        }
    }

}
