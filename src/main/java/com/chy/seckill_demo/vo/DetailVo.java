package com.chy.seckill_demo.vo;

import com.chy.seckill_demo.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chy
 * @Date: 2022/4/18 21:18
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {

    private User user;

    private GoodsVo goodsVo;

    private int secKillStatus;

    private int remainSeconds;
}
