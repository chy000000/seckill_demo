package com.chy.seckill_demo.vo;

import com.chy.seckill_demo.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:50
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
