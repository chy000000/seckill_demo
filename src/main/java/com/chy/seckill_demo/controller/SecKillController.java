package com.chy.seckill_demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chy.seckill_demo.pojo.Order;
import com.chy.seckill_demo.pojo.SeckillMessage;
import com.chy.seckill_demo.pojo.SeckillOrder;
import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.rabbitmq.MQSender;
import com.chy.seckill_demo.service.IGoodsService;
import com.chy.seckill_demo.service.IOrderService;
import com.chy.seckill_demo.service.ISeckillOrderService;
import com.chy.seckill_demo.service.IUserService;
import com.chy.seckill_demo.utils.JsonUtil;
import com.chy.seckill_demo.vo.GoodsVo;
import com.chy.seckill_demo.vo.RespBean;
import com.chy.seckill_demo.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:42
 * @Description:
 */
@Controller
@RequestMapping({"/seckill"})
public class SecKillController implements InitializingBean {
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
    @Autowired
    MQSender mqSender;
    @Autowired
    RedisScript<Long> script;
    //内存标记
    private Map<Long, Boolean> emptyStock = new HashMap<>();

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
        ValueOperations valueOperations = redisTemplate.opsForValue();
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }
        // 内存标记
        if (emptyStock.get(goodsId)) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
//        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        Long stock = (Long) redisTemplate.execute(script, Collections.singletonList("seckillGoods:" + goodsId), Collections.EMPTY_LIST);
        if (stock < 0) {
            emptyStock.put(goodsId, true);
//            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return RespBean.success(0);

//        GoodsVo goods = this.goodsService.findGoodsVoByGoodsId(goodsId);
//        if (goods.getStockCount() < 1) {
//            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
//        }
////            SeckillOrder seckillOrder = (SeckillOrder)this.seckillOrderService.getOne((Wrapper)((QueryWrapper)(new QueryWrapper()).eq("user_id", user.getId())).eq("goods_id", goodsId));
//        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goods.getId());
//        if (seckillOrder != null) {
//            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
//        }
//        Order order = this.orderService.seckill(user, goods);
//        return RespBean.success(order);
    }

    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(HttpServletRequest request, HttpServletResponse response, Long goodsId,
                              @CookieValue("userTicket") String ticket) {
        User user = userService.getUserByCookie(ticket, request, response);
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            emptyStock.put(goodsVo.getId(), false);
        });
    }
}
