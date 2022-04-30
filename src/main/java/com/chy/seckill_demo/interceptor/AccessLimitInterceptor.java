package com.chy.seckill_demo.interceptor;

import com.chy.seckill_demo.config.AccessLimit;
import com.chy.seckill_demo.config.UserContext;
import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.service.IUserService;
import com.chy.seckill_demo.utils.CookieUtil;
import com.chy.seckill_demo.vo.RespBean;
import com.chy.seckill_demo.vo.RespBeanEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.spring5.processor.SpringActionTagProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chy
 * @Date: 2022/4/30 10:12
 * @Description:
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String ticket = CookieUtil.getCookieValue(request, "userTicket");
//        User user = userService.getUserByCookie(ticket, request, response);
        User user = UserContext.getUser();
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int second = accessLimit.second();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    render(request, response, RespBeanEnum.SESSION_ERROR);
                    return false;
                }
                key += ":" + user.getId();
            }
            Integer count = (Integer) redisTemplate.opsForValue().get(key);
            if (count == null) {
                redisTemplate.opsForValue().set(key, 1, 5, TimeUnit.SECONDS);
            } else if (count < 5) {
                redisTemplate.opsForValue().increment(key);
            } else {
                render(request, response, RespBeanEnum.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletRequest request, HttpServletResponse response, RespBeanEnum sessionError) throws IOException {
        response.setContentType("application.json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        RespBean respBean = RespBean.error(sessionError);
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
