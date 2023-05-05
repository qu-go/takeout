package com.xinzhou.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

//@Configuration
//@EnableWebSocketMessageBroker
public class  WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //配置基站节点
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //配置客户端链接地址
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //设置广播节点
        registry.enableSimpleBroker("/topic");
        //配置客户端向服务端发送请求的前缀
        registry.setApplicationDestinationPrefixes("/app");
//        //配置用户指定发送的前缀（一对一）
//        registry.setUserDestinationPrefix("/user");
    }
}
