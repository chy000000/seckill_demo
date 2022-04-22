package com.chy.seckill_demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:54
 * @Description:
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),
    LOGIN_ERROR(500210, "用户名或密码不正确"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212, "校验参数异常"),
    MOBILE_NOT_EXIST(500213, "手机号不存在"),
    PASSWORD_UQDATE_FAIL(500214, "更新密码失败"),
    EMPTY_STOCK(500500, "库存不足"),
    REPEAT_ERROR(500501, "该商品每人限购一件"),
    ORDER_NOT_EXIST(500300, "订单信息不存在"),
    ;

    private final Integer code;
    private final String message;

}
