package com.chy.seckill_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.vo.LoginVo;
import com.chy.seckill_demo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chy
 * @Date: 2022/4/16 10:30
 * @Description:
 */
public interface IUserService extends IService<User> {
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);

    RespBean updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response);
}
