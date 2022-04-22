package com.chy.seckill_demo.interceptor;

import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:37
 * @Description:
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String ticket = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie[] var6 = cookies;
            int var7 = cookies.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Cookie cookie = var6[var8];
                if (cookie.getName().equals("userTicket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket == null) {
            response.sendRedirect("/login/toLogin");
            return false;
        } else {
            User user = this.userService.getUserByCookie(ticket, request, response);
            if (null == user) {
                response.sendRedirect("/login/toLogin");
                return false;
            } else {
                return true;
            }
        }
    }
}
