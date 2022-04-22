package com.chy.seckill_demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chy.seckill_demo.pojo.Order;
import com.chy.seckill_demo.pojo.SeckillOrder;
import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.service.IGoodsService;
import com.chy.seckill_demo.service.IOrderService;
import com.chy.seckill_demo.service.ISeckillOrderService;
import com.chy.seckill_demo.service.IUserService;
import com.chy.seckill_demo.vo.GoodsVo;
import com.chy.seckill_demo.vo.RespBean;
import com.chy.seckill_demo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:42
 * @Description:
 */
@Controller
@RequestMapping({"/seckill"})
public class SecKillController {
    @Autowired
    IUserService userService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    ISeckillOrderService seckillOrderService;
    @Autowired
    IOrderService orderService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * window 优化前：974.2
     * @param request
     * @param response
     * @param model
     * @param goodsId
     * @param ticket
     * @return
     */
    @RequestMapping({"/doSeckill2"})
    public String doSeckill2(HttpServletRequest request, HttpServletResponse response, Model model, Long goodsId, @CookieValue("userTicket") String ticket) {
        User user = this.userService.getUserByCookie(ticket, request, response);
        model.addAttribute("user", user);
        GoodsVo goods = this.goodsService.findGoodsVoByGoodsId(goodsId);
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        } else {
            SeckillOrder seckillOrder = (SeckillOrder)this.seckillOrderService.getOne((Wrapper)((QueryWrapper)(new QueryWrapper()).eq("user_id", user.getId())).eq("goods_id", goodsId));
            if (seckillOrder != null) {
                model.addAttribute("errmsg", RespBeanEnum.REPEAT_ERROR.getMessage());
                return "secKillFail";
            } else {
                Order order = this.orderService.seckill(user, goods);
                model.addAttribute("order", order);
                model.addAttribute("goods", goods);
                return "orderDetail";
            }
        }
    }

    /**
     * window 优化前：974.2
     * @param request
     * @param response
     * @param goodsId
     * @param ticket
     * @return
     */
    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(HttpServletRequest request, HttpServletResponse response, Long goodsId,
                              @CookieValue("userTicket") String ticket) {
        User user = this.userService.getUserByCookie(ticket, request, response);
        GoodsVo goods = this.goodsService.findGoodsVoByGoodsId(goodsId);
        if (goods.getStockCount() < 1) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
//            SeckillOrder seckillOrder = (SeckillOrder)this.seckillOrderService.getOne((Wrapper)((QueryWrapper)(new QueryWrapper()).eq("user_id", user.getId())).eq("goods_id", goodsId));
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goods.getId());
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        Order order = this.orderService.seckill(user, goods);
        return RespBean.success(order);
    }
}
