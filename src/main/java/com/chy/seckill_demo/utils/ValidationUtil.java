package com.chy.seckill_demo.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:52
 * @Description:
 */
public class ValidationUtil {
    private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");


    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        } else {
            Matcher matcher = mobile_pattern.matcher(mobile);
            return matcher.matches();
        }
    }
}
