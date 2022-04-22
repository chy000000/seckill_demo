package com.chy.seckill_demo.vo;

import com.chy.seckill_demo.utils.ValidationUtil;
import com.chy.seckill_demo.validator.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:51
 * @Description:
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = true;
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (this.required) {
            return ValidationUtil.isMobile(value);
        } else {
            return StringUtils.isEmpty(value) ? false : ValidationUtil.isMobile(value);
        }
    }
}
