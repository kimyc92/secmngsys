package com.secmngsys.global.aop;

import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.exception.GenericSuccessCustomException;
import com.secmngsys.global.exception.GenericErrorCustomException;
import com.secmngsys.global.util.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DaoResponseValidatorAspect {

//    Validator validator;
//
//    @Autowired
//    public DaoResponseValidatorAspect(Validator validator) {
//        this.validator = validator;
//    }

    @AfterReturning(pointcut = "execution(* com.secmngsys.domain..*..dao..*..selectOne*(..))" +
                            "|| execution(* com.secmngsys.domain..*..dao..*..select*One*(..))"
                            , returning = "result")
    public void daoSelectOneResponseValidate(JoinPoint joinPoint, Object result) {
        log.info("daoResponseValidate - "+joinPoint.toLongString());
        daoSelectOneResponseValidate(result);
    }

    private void daoSelectOneResponseValidate(Object object) {
        log.info("daoSelectOneResponseValidate()");
        if(ConvertUtils.convertObjectToList(object).size() < 1)
            throw new GenericSuccessCustomException("요청 결과가 없습니다.", SuccessCode.NO_CONTENT);
            //throw new GenericErrorCustomException("요청 결과가 없습니다.", ErrorCode.EXCEPTION);
    }
}
