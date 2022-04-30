package com.chy.seckill_demo.config;

import com.chy.seckill_demo.interceptor.AccessLimitInterceptor;
import com.chy.seckill_demo.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: chy
 * @Date: 2022/4/15 21:36
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;
    @Autowired
    AccessLimitInterceptor accessLimitInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/bootstrap/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/jquery-validation/**")
                .excludePathPatterns("/layer/**")
                .excludePathPatterns("/goods/toList");
        registry.addInterceptor(this.accessLimitInterceptor);
    }




}
