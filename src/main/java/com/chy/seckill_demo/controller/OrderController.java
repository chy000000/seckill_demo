package com.chy.seckill_demo.controller;

import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.service.IOrderService;
import com.chy.seckill_demo.service.IUserService;
import com.chy.seckill_demo.vo.OrderDetailVo;
import com.chy.seckill_demo.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:41
 * @Description:
 */
@Controller
@RequestMapping({"/order"})
public class OrderController {

    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;

    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(HttpServletRequest request, HttpServletResponse response
            , Long orderId, @CookieValue("userTicket") String ticket) {
        User user = this.userService.getUserByCookie(ticket, request, response);
        OrderDetailVo detailVo = orderService.detail(orderId);
        return RespBean.success(detailVo);
    }
}
