package com.secmngsys.global.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE
        , ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")
//@Pattern(regexp = "(\\w)\\1\\1\\1")
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
public @interface Password {
    String message() default "비밀번호는 숫자, 문자, 특수문자 포함 8~15자리 이내 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
