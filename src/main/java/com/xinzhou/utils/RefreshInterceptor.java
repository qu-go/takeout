package com.xinzhou.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.xinzhou.dto.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RefreshInterceptor implements HandlerInterceptor {
    public StringRedisTemplate stringRedisTemplate;

    public RefreshInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //todo:用redis登陆校验
        //1. 取出token，头
        String token = request.getHeader("authorization");
        //2. 判断是否为空
        //2.1 为空直接放行

        if (StrUtil.isBlank(token)){
            return true;
        }

        //2.2 不为空在redis取出useMAp
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(RedisConstant.LOGIN_TOKEN_KEY + token);
        System.out.println(userMap.get("id"));
        //3.判断取出的useMap
        //3.1 如果为空直接放行
        if (userMap.isEmpty()){
            return true;
        }

        //3.2 如果不为空转化为useDTO
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(),false);
        //4.保存useDTO在threadLocal
        UserHolder.save(userDTO);
        //刷新有效期
        stringRedisTemplate.expire(RedisConstant.LOGIN_TOKEN_KEY+token, RedisConstant.LOGIN_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
        //放行
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
