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
@Order(value=1)   // 다른 어떤 AOP 보다 먼저
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

    // com.springboot.batch 하위 패키지 내 dao라는 이름의 패키지를 포함한 하위 패키지 중 selectTarget로 시작되는 메서드에서 동작
    /*
    @Pointcut("execution(* com.springboot.batch..dao..*.selectTarget*(..))" +
           "|| execution(* com.springboot.batch..dao..*.updateTarget*(..))" +
           "|| execution(* com.springboot.batch..dao..*.insertTarget*(..))" +
           "|| execution(* com.springboot.batch..dao..*.deleteTarget*(..))")
    private void targetPointCut() {}
    */
    @Pointcut("execution(* com.secmngsys..*..selectSmsDb*(..))" +
           "|| execution(* com.secmngsys..*..updateSmsDb*(..))" +
           "|| execution(* com.secmngsys..*..insertSmsDb*(..))" +
           "|| execution(* com.secmngsys..*..deleteSmsDb*(..))"
            //+
           //"|| execution(* com.springboot.batch.service.job.SQLTransJob01.dao.Step3Dao.*(..))"
    )
//    @Pointcut("execution(* com.springboot.batch..dao..*.selectTarget*(..))" +
//            "|| execution(* com.springboot.batch..dao..*.updateTarget*(..))" +
//            "|| execution(* com.springboot.batch..dao..*.insertTarget*(..))" +
//            "|| execution(* com.springboot.batch..dao..*.deleteTarget*(..))")
    private void smsDbPointCut() {}

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
//            //System.out.println("t11111111111111 - "+rdi.toString());
//            routingDatabaseInfo.RoutingDatabaseInfo(rdi.getJdbcUrl(), rdi.getDriverClassName(), rdi.getUsername(), rdi.getPassword());
//            System.out.println("t라우팅 전 데이터 확인 - "+RoutingDatabaseContextHolder.get());

            //System.out.println("비교 - 1 "+);
            //if(1==1) throw new RoutingDataSourceException("SMS DB Routing Error");
           // int zz = 1/0;
            System.out.println("RoutingDatabaseAspect.smsDbPointCutAround()!!!!!!!!!!!!!!!!!");
            RoutingDatabaseContextHolder.set(DatabaseTypeCode.Sms);
            Object result = pjp.proceed(); //핵심 기능 호출 == (이전예제) delegate.factorial(20);
            //if(1==1) throw new RoutingDataSourceException("SMS DB Routing Error");
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
            /*
            RoutingDatabaseInfo rdi = RoutingDatabaseContextHolder.getRoutingDBInfo(DatabaseTypeCode.Source);
            System.out.println("s11111111111111 - "+rdi.toString());
            routingDatabaseInfo.RoutingDatabaseInfo(rdi.getJdbcUrl(), rdi.getDriverClassName(), rdi.getUsername(), rdi.getPassword());
            System.out.println("s라우팅 전 데이터 확인 - "+RoutingDatabaseContextHolder.getRoutingDBInfo());
            */

//            RoutingDatabaseConfig routingDatabaseConfig = new RoutingDatabaseConfig();
//            RoutingDatabaseInfo rdi = (RoutingDatabaseInfo) routingDatabaseConfig.dataSources.get(DatabaseTypeCode.Source);
//            //System.out.println("t11111111111111 - "+rdi.toString());
//            routingDatabaseInfo.RoutingDatabaseInfo(rdi.getJdbcUrl(), rdi.getDriverClassName(), rdi.getUsername(), rdi.getPassword());
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
