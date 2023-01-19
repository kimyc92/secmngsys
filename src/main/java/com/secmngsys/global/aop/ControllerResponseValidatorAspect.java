package com.secmngsys.global.aop;

import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.exception.GenericSuccessCustomException;
import com.secmngsys.global.model.ResponseSuccess;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Aspect
@Component
public class ControllerResponseValidatorAspect {

    Validator validator;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public ControllerResponseValidatorAspect(Validator validator) {
        this.validator = validator;
    }

    @AfterReturning(pointcut = "execution(* com.secmngsys.domain..*..controller.*.*(..))", returning = "result")
    public void controllerResponseValidate(JoinPoint joinPoint, ResponseEntity<ResponseSuccess> result) {
        log.info("ControllerResponseValidate - "+joinPoint.toLongString());
        controllerResponseValidate(result);
    }

    private void controllerResponseValidate(ResponseEntity<ResponseSuccess> object) {
        //Map<String, Object> map = (Map<String, Object>) object;
//        System.out.println("ControllerResponseValidatorAspect");
//        System.out.println("getBody!!!!!!!!!!!" + object.getBody());
//        System.out.println("getStatusCode!!!!!!!!!!!" + object.getStatusCode());
//        System.out.println("getStatusCodeValue!!!!!!!!!!!" + object.getStatusCodeValue());
//        System.out.println("getHeaders!!!!!!!!!!!" + object.getHeaders());
        Set<ConstraintViolation<Object>> validationResults = validator.validate(object);
        //System.out.println(validationResults.size());
        if (validationResults.size() > 0) {

            StringBuffer sb = new StringBuffer();

            for (ConstraintViolation<Object> error : validationResults) {
                sb.append(error.getPropertyPath()).append(" - ").append(error.getMessage()).append("\n");
            }

            String msg = sb.toString();
            log.error(msg);
            throw new GenericSuccessCustomException("요청 결과가 없습니다.", SuccessCode.NO_CONTENT);
        }
    }

}
