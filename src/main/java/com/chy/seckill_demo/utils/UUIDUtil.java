package com.chy.seckill_demo.utils;

import java.util.UUID;

/**
 * @Author: chy
 * @Date: 2022/4/15 22:39
 * @Description:
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
