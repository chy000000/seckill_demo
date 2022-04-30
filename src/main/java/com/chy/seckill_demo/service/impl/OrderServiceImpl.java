package com.chy.seckill_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.seckill_demo.exception.GlobalException;
import com.chy.seckill_demo.mapper.OrderMapper;
import com.chy.seckill_demo.pojo.Order;
import com.chy.seckill_demo.pojo.SeckillGoods;
import com.chy.seckill_demo.pojo.SeckillOrder;
import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.service.IGoodsService;
import com.chy.seckill_demo.service.IOrderService;
import com.chy.seckill_demo.service.ISeckillGoodsService;
import com.chy.seckill_demo.service.ISeckillOrderService;
import com.chy.seckill_demo.utils.MD5Util;
import com.chy.seckill_demo.utils.UUIDUtil;
import com.chy.seckill_demo.vo.GoodsVo;
import com.chy.seckill_demo.vo.OrderDetailVo;
import com.chy.seckill_demo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chy
 * @Date: 2022/4/16 10:42
 * @Description:
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goods) {
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>()
                .eq("goods_id", goods.getId()));
//        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        boolean seckillGoodsResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .eq("goods_id", goods.getId())
                .gt("stock_count", 0)
                .setSql("stock_count = stock_count - 1"));
        //判断是否还有库存，没有则在redis中设置key
        if (seckillGoods.getStockCount() < 1) {
            redisTemplate.opsForValue().set("isEmptyStock:"+goods.getId(), "0");
            return null;
        }
        // 判断是否还有库存
//        if (!seckillGoodsResult) {
//            return null;
//        }
//        seckillGoodsService.updateById(seckillGoods);
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:"+user.getId()+":"+goods.getId(), seckillOrder);
        return order;
    }

    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId==null) {
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo orderDetail = new OrderDetailVo(order, goodsVo);
        return orderDetail;
    }

    @Override
    public String createPath(User user, Long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:"+user.getId()+":"+goodsId, str, 60, TimeUnit.SECONDS);
        return str;
    }

    @Override
    public boolean checkPath(User user, Long goodsId, String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        String redisPath = (String) redisTemplate.opsForValue().get("seckillPath:" + user.getId() + ":" + goodsId);
        return path.equals(redisPath);
    }

    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        if (StringUtils.isEmpty(captcha)) {
            return false;
        }
        String redisCaptcha = (String) redisTemplate.opsForValue().get("captcha:" + user.getId() + ":" + goodsId);
        log.info("输入captcha：" + captcha + "，正确captcha：" + redisCaptcha);
        return captcha.equals(redisCaptcha);
    }
}
