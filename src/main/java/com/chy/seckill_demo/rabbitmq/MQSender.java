package com.chy.seckill_demo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chy
 * @Date: 2022/4/23 14:57
 * @Description:
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendSeckillMessage(String message) {
        log.info("发送消息： "+message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", message);
    }
}
