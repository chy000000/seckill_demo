package com.chy.seckill_demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:53
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    public static RespBean success() {
        return new RespBean((long)RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), (Object)null);
    }

    public static RespBean success(Object obj) {
        return new RespBean((long)RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), obj);
    }

    public static RespBean error(RespBeanEnum respBeanEnum) {
        return new RespBean((long)respBeanEnum.getCode(), respBeanEnum.getMessage(), (Object)null);
    }

    public static RespBean error(RespBeanEnum respBeanEnum, Object obj) {
        return new RespBean((long)respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }
}
