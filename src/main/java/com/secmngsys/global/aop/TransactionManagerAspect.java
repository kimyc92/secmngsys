package com.secmngsys.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.DeclareParentsAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.*;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collections;
import java.util.HashMap;

@Slf4j
@Aspect
@Configuration
public class TransactionManagerAspect {
//
//    @Autowired
//    private PlatformTransactionManager transactionManager;
//    private static final String AOP_TRANSACTION_EXPRESSION="execution(* com.secmngsys..*..smsSends*(..))";
//
//    @Pointcut(AOP_TRANSACTION_EXPRESSION)
//    //@Pointcut("execution(* com.secmngsys..*..*.*(..))")
////            "|| execution(* com.secmngsys..*..selectSmsDb*(..))" +
////            "|| execution(* com.secmngsys..*..updateSmsDb*(..))" +
////            "|| execution(* com.secmngsys..*..insertSmsDb*(..))" +
////            "|| execution(* com.secmngsys..*..deleteSmsDb*(..))")
//    private void txPointCut() {}
//
//    private TransactionDefinition getDefinition(int isolationLevel,
//                                                boolean isReadOnly) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition(
//                TransactionDefinition.PROPAGATION_REQUIRED);
//        def.setTimeout(5000);
//        def.setReadOnly(isReadOnly);
//        def.setIsolationLevel(isolationLevel);
//        return def;
//    }
//
//    @AfterThrowing(value = "txPointCut()", throwing = "e")
//    public void txPointCut(JoinPoint joinPoint, Throwable e) {
//        log.info("Target PointCut AfterThrowing Debug");
//        System.out.println("Target PointCut AfterThrowing Debug!@!@!@!@!@!@!@!@!");
//        System.out.println(joinPoint.getKind());
//        System.out.println(joinPoint.getThis().toString());
//        System.out.println(joinPoint.getThis().toString());
//
//        //DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        //DefaultTransactionDefinition def = transactionManager.getTransaction()
//
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition(
//                TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(
//                def
//        );
//
//        //System.out.println("def @!- "+def.getName());
//        System.out.println("status @!- "+status);
//
//        transactionManager.rollback(status);
//        //transactionManager.getTransaction()
//        //log.info("Throwable : "+e); //
//
//    }
//
//    @Bean
//    public Advisor transactionAdiceAdvisor() {
//        log.info("transactionAdiceAdvisor()");
//        System.out.println("transactionAdiceAdvisor()");
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
//        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
//    }
//
//    @Bean
//    public TransactionInterceptor transactionAdvice() {
//        log.info("transactionAdvice()");
//        System.out.println("transactionAdvice()");
//        TransactionInterceptor txAdvice = new TransactionInterceptor();
//        NameMatchTransactionAttributeSource txAttribueSource = new NameMatchTransactionAttributeSource();
//
//        HashMap<String, TransactionAttribute> txMethods = new HashMap<>();
//        RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
//        txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
//        txAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        txMethods.put("*", txAttribute);
//
////        RuleBasedTransactionAttribute txAttribute2 = new RuleBasedTransactionAttribute();
////        txAttribute2.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
////        txAttribute2.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
////        txMethods.put("*", txAttribute2);
//
////        RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
////        txMethods.put("insert*", txAttribute.set
//        txAttribueSource.setNameMap(txMethods);
//
//        System.out.println("txMethods - "+txMethods);
//
//        txAdvice.setTransactionAttributeSource(txAttribueSource);
//        txAdvice.setTransactionManager(transactionManager);
//        return txAdvice;
//    }
}
