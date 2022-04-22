package com.chy.seckill_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chy.seckill_demo.mapper.GoodsMapper;
import com.chy.seckill_demo.pojo.Goods;
import com.chy.seckill_demo.service.IGoodsService;
import com.chy.seckill_demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chy
 * @Date: 2022/4/16 10:30
 * @Description:
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;


    public List<GoodsVo> findGoodsVo() {
        return this.goodsMapper.findGoodsVo();
    }

    public GoodsVo findGoodsVoByGoodsId(Long goodsId) {
        return this.goodsMapper.findGoodsVoByGoodsId(goodsId);
    }
}
