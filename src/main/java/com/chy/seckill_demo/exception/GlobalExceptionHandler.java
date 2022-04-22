package com.chy.seckill_demo.exception;

import com.chy.seckill_demo.vo.RespBean;
import com.chy.seckill_demo.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:45
 * @Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public RespBean ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return RespBean.error(ex.getRespBeanEnum());
        } else if (e instanceof BindException) {
            BindException ex = (BindException)e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常: " + ((ObjectError)ex.getBindingResult().getAllErrors().get(0)).getDefaultMessage());
            return respBean;
        } else {
            return RespBean.error(RespBeanEnum.ERROR);
        }
    }
}
