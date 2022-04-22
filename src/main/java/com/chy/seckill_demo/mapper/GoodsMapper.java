package com.chy.seckill_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chy.seckill_demo.pojo.Goods;
import com.chy.seckill_demo.vo.GoodsVo;

import java.util.List;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:46
 * @Description:
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
