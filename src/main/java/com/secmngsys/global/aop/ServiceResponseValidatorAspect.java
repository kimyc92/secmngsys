package com.secmngsys.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceResponseValidatorAspect {

//    Validator validator;
//
//    @Autowired
//    public ServiceResponseValidatorAspect(Validator validator) {
//        this.validator = validator;
//    }

    //@AfterReturning(pointcut = "execution(* com.secmngsys.domain..*..service.*.*(..))", returning = "result")
//    public void serviceResponseValidate(JoinPoint joinPoint, List<UserVo> result) {
//        log.info("ServiceResponseValidate - "+joinPoint.toLongString());
//        serviceResponseValidate(result);
//    }
//
//    private void serviceResponseValidate(List<UserVo> object) {
//        //Map<String, Object> map = (Map<String, Object>) object;
//        System.out.println("ServiceResponseValidatorAspect");
//        System.out.println("getBody!!!!!!!!!!!" + object.get(0).getUserId());
//        Set<ConstraintViolation<Object>> validationResults = validator.validate(object);
//        System.out.println("asdasdads");
//        //System.out.println(validationResults.size());
//        if (validationResults.size() > 0) {
//
//            StringBuffer sb = new StringBuffer();
//
//            for (ConstraintViolation<Object> error : validationResults) {
//                sb.append(error.getPropertyPath()).append(" - ").append(error.getMessage()).append("\n");
//            }
//
//            String msg = sb.toString();
//            log.error(msg);
//            throw new CustomSuccessException("요청 결과가 없습니다.", SuccessCode.NO_CONTENT);
//        }
//    }

}
