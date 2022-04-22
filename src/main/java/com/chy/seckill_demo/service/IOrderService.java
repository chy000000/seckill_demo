package com.chy.seckill_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.seckill_demo.pojo.Order;
import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.vo.GoodsVo;
import com.chy.seckill_demo.vo.OrderDetailVo;

/**
 * @Author: chy
 * @Date: 2022/4/16 10:28
 * @Description:
 */
public interface IOrderService  extends IService<Order> {
    Order seckill(User user, GoodsVo goods);

    OrderDetailVo detail(Long orderId);
}
