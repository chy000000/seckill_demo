package com.chy.seckill_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.seckill_demo.exception.GlobalException;
import com.chy.seckill_demo.mapper.UserMapper;
import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.service.IUserService;
import com.chy.seckill_demo.utils.CookieUtil;
import com.chy.seckill_demo.utils.MD5Util;
import com.chy.seckill_demo.utils.UUIDUtil;
import com.chy.seckill_demo.vo.LoginVo;
import com.chy.seckill_demo.vo.RespBean;
import com.chy.seckill_demo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chy
 * @Date: 2022/4/16 10:52
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;


    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        User user = (User)this.userMapper.selectById(mobile);
        if (null == user) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        } else if (!MD5Util.fromPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        } else {
            String ticket = UUIDUtil.uuid();
            this.redisTemplate.opsForValue().set("user:" + ticket, user);
            CookieUtil.setCookie(request, response, "userTicket", ticket);
            return RespBean.success(ticket);
        }
    }

    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        } else {
            User user = (User)this.redisTemplate.opsForValue().get("user:" + userTicket);
            return user;
        }
    }

    @Override
    public RespBean updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if (user==null) {
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSlat()));
        int result = userMapper.updateById(user);
        if (result!=1) {
            return RespBean.error(RespBeanEnum.PASSWORD_UQDATE_FAIL);
        }
        redisTemplate.delete("user:"+userTicket);
        return RespBean.success();
    }
}
