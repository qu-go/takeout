package com.xinzhou.Interceptor;

import cn.hutool.log.Log;
import com.xinzhou.utils.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.logging.Logger;

@Slf4j
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public WebSocketHandShakeInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate=stringRedisTemplate;
    }


    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {


        //TODO
        //1. 在用户登录成功后存储token，设置过期时间
        //2. 然后给登录成功的用户建立ws连接，建立连接根据自己的身份携带参数useid和storeid
        //3. 在拦截器中在协议握手前验证token的正确性，正确就放行
        //4. 将id信息和token放入map里面传递
        //5.保存用户在线信息
        //6.当建立起连接的时候用户的在线人数加一
        //7. 用户发起订单给商家，订单创建成功，发送信息给商家
        //8. 商家处理订单，然后接收订单，发送信息给指定的用户
        //9. 通过message进行通信

        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            //通过url传参的方式
            String userId = request.getServletRequest().getParameter("userId");
            String identity = request.getServletRequest().getParameter("identity");
            //通过header来传递
            String token1 = request.getServletRequest().getHeader("token");
            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(token1)) {
                serverHttpResponse.setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                return false;
            }
            //redis取出token
            Map<Object, Object> userDTO = stringRedisTemplate.opsForHash().entries(RedisConstant.LOGIN_TOKEN_KEY + token1);
            if (userDTO == null) {
                serverHttpResponse.setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                return false;
            }
            map.put("userId", userId);
            map.put("token", token1);
            map.put("identity", identity);
            // String parameter = request.getServletRequest().getHeader("Sec-WebSocket-Protocol");
            //通过定义
            // log.info("这个是token{}",parameter);
            return true;
        }


        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
