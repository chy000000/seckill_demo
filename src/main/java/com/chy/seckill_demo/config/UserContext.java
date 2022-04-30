package com.chy.seckill_demo.config;

import com.chy.seckill_demo.pojo.User;

/**
 * @Author: chy
 * @Date: 2022/4/30 10:24
 * @Description:
 */
public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }
}
