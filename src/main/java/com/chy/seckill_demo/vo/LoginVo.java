package com.chy.seckill_demo.vo;

import com.chy.seckill_demo.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:53
 * @Description:
 */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
