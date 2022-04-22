package com.chy.seckill_demo.controller;

import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.service.IGoodsService;
import com.chy.seckill_demo.service.IUserService;
import com.chy.seckill_demo.vo.DetailVo;
import com.chy.seckill_demo.vo.GoodsVo;
import com.chy.seckill_demo.vo.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:40
 * @Description:
 */
@Controller
@RequestMapping({"/goods"})
public class GoodsController {
    @Autowired
    IUserService userService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

//    /**
//     *1121
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping("/toList")
////    @ResponseBody
//    public String toList(HttpServletRequest request, HttpServletResponse response, Model model) {
////        User user = this.userService.getUserByCookie(ticket, request, response);
//        List<GoodsVo> goodsList = this.goodsService.findGoodsVo();
////        model.addAttribute("user", user);
//        model.addAttribute("goodsList", goodsList);
//        return "goodsList";
//    }

    /**
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
//        User user = this.userService.getUserByCookie(ticket, request, response);
        List<GoodsVo> goodsList = this.goodsService.findGoodsVo();
//        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsList);

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
//        return "goodsList";
        return html;
    }

    @RequestMapping(value = "/toDetail2/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail2(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long goodsId, @CookieValue("userTicket") String ticket) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String  html = (String) valueOperations.get("goodsDetail" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        User user = this.userService.getUserByCookie(ticket, request, response);
        model.addAttribute("user", user);
        GoodsVo goods = this.goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date nowDate = new Date();
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if (nowDate.before(startDate)) {
            remainSeconds = (int)((startDate.getTime() - nowDate.getTime()) / 1000L);
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("goods", goods);
        model.addAttribute("secKillStatus", Integer.valueOf(secKillStatus));
        model.addAttribute("remainSeconds", remainSeconds);

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsDetail" + goodsId, html, 60, TimeUnit.SECONDS);
        }
        return html;
//        return "goodsDetail";
    }

    @RequestMapping("/toDetail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable Long goodsId, @CookieValue("userTicket") String ticket) {
        User user = this.userService.getUserByCookie(ticket, request, response);
        GoodsVo goodsVo = this.goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if (nowDate.before(startDate)) {
            remainSeconds = (int)((startDate.getTime() - nowDate.getTime()) / 1000L);
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }

        DetailVo detailVo = new DetailVo(user, goodsVo, secKillStatus, remainSeconds);
        return RespBean.success(detailVo);
    }
}
