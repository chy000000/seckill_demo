package com.chy.seckill_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.seckill_demo.mapper.SeckillOrderMapper;
import com.chy.seckill_demo.pojo.SeckillOrder;
import com.chy.seckill_demo.service.ISeckillOrderService;
import org.springframework.stereotype.Service;

/**
 * @Author: chy
 * @Date: 2022/4/16 10:52
 * @Description:
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {
}
