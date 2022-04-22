package com.chy.seckill_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:39
 * @Description:
 */
@Controller
@RequestMapping({"demo"})
public class DemoController {
    @RequestMapping({"/hello"})
    public String hello(Model model) {
        model.addAttribute("name", "chy");
        return "hello";
    }
}
