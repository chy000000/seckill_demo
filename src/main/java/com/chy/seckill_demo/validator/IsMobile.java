package com.chy.seckill_demo.validator;

import com.chy.seckill_demo.vo.IsMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:52
 * @Description:
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {
    boolean required() default true;

    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
