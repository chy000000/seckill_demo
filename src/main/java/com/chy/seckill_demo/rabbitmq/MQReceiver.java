package com.chy.seckill_demo.rabbitmq;

import com.chy.seckill_demo.pojo.SeckillMessage;
import com.chy.seckill_demo.pojo.SeckillOrder;
import com.chy.seckill_demo.pojo.User;
import com.chy.seckill_demo.service.IGoodsService;
import com.chy.seckill_demo.service.IOrderService;
import com.chy.seckill_demo.utils.JsonUtil;
import com.chy.seckill_demo.vo.GoodsVo;
import com.chy.seckill_demo.vo.RespBean;
import com.chy.seckill_demo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: chy
 * @Date: 2022/4/23 15:00
 * @Description:
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    IGoodsService goodsService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IOrderService orderService;

    @RabbitListener(queues = "seckillQueue")
    public void receiveSeckillMessage(String msg) {
        log.info("接收消息： " + msg);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount()<1) {
            return;
        }
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return;
        }
        orderService.seckill(user, goodsVo);
    }
}
