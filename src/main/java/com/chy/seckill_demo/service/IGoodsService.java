package com.chy.seckill_demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chy.seckill_demo.pojo.Goods;
import com.chy.seckill_demo.vo.GoodsVo;

import java.util.List;

/**
 * @Author: chy
 * @Date: 2022/4/15 22:40
 * @Description:
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
