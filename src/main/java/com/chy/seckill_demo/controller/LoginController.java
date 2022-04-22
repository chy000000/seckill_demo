package com.chy.seckill_demo.controller;

import com.chy.seckill_demo.service.IUserService;
import com.chy.seckill_demo.vo.LoginVo;
import com.chy.seckill_demo.vo.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:41
 * @Description:
 */
@Controller
@RequestMapping({"/login"})
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    IUserService userService;

    @RequestMapping({"/toLogin"})
    public String toLogin() {
        return "login";
    }

    @RequestMapping({"/doLogin"})
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        return this.userService.doLogin(loginVo, request, response);
    }
}
