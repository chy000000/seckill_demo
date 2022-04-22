package com.chy.seckill_demo.vo;

import com.chy.seckill_demo.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chy
 * @Date: 2022/4/19 10:42
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}
