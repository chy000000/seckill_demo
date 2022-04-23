package com.chy.seckill_demo.controller;

import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.rabbitmq.MQSender;
import com.chy.seckill_demo.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:43
 * @Description:
 */
@Controller
@RequestMapping({"/user"})
public class UserController {
    @Autowired
    MQSender sender;


    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user) {
        return RespBean.success(user);
    }


    @RequestMapping("/mq")
    @ResponseBody
    public void mq() {
        sender.send("Hello world!");
    }
}
