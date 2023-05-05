package com.xinzhou.config;

import com.xinzhou.utils.LoginInterceptor;
import com.xinzhou.utils.RefreshInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns("/user/login","/user/info","/user/add/**"
                ,"/goods/**","/store/**",
                "/car/**","/car/delete/ids",
                "/comment/**","/store/add",
                "/store/login","/goods/search"

        )
                .order(1);

        registry.addInterceptor(new RefreshInterceptor(stringRedisTemplate))
                .addPathPatterns("/**").order(0);
    }


    //配置全局跨域


}
