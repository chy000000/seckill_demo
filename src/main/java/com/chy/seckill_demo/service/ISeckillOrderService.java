package com.chy.seckill_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.seckill_demo.pojo.SeckillOrder;
import com.chy.seckill_demo.pojo.User;

/**
 * @Author: chy
 * @Date: 2022/4/16 10:29
 * @Description:
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Long getResult(User user, Long goodsId);
}
