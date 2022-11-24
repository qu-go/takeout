package com.xinzhou.config;

import com.xinzhou.Interceptor.WebSocketHandShakeInterceptor;
import com.xinzhou.handler.HttpAuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;
import javax.websocket.server.ServerEndpoint;

@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler(), "myHandler")
                                .addInterceptors(new WebSocketHandShakeInterceptor(stringRedisTemplate))
        .setAllowedOrigins("*");
    }

    @Bean
    public HttpAuthHandler handler(){
        return new HttpAuthHandler();
    }
}
