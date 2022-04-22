package com.chy.seckill_demo.exception;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:43
 * @Description:
 */

import com.chy.seckill_demo.vo.RespBeanEnum;

public class GlobalException extends RuntimeException {
    private RespBeanEnum respBeanEnum;

    public RespBeanEnum getRespBeanEnum() {
        return this.respBeanEnum;
    }

    public void setRespBeanEnum(final RespBeanEnum respBeanEnum) {
        this.respBeanEnum = respBeanEnum;
    }

    public String toString() {
        return "GlobalException(respBeanEnum=" + this.getRespBeanEnum() + ")";
    }


    public GlobalException(final RespBeanEnum respBeanEnum) {
        this.respBeanEnum = respBeanEnum;
    }
}

